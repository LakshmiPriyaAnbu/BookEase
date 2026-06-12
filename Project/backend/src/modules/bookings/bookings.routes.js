const router = require('express').Router();
const { z } = require('zod');
const { v4: uuidv4 } = require('uuid');
const pool = require('../../config/db');
const { requireAuth, requireRole } = require('../../middleware/auth');

const createSchema = z.object({
  serviceId: z.string().uuid(),
  coachId: z.string().uuid(),
  startsAt: z.string().datetime(),
  fullName: z.string().min(2),
  phone: z.string(),
  notes: z.string().optional(),
});

const updateSchema = z.object({
  status: z.enum(['pending', 'confirmed', 'completed', 'cancelled']),
});

function makeRef() {
  return 'BK-' + Math.floor(10000 + Math.random() * 90000);
}

// GET /api/bookings   (my bookings)
router.get('/', requireAuth, async (req, res, next) => {
  try {
    const { status } = req.query;
    const params = [req.user.sub];
    let statusFilter = '';
    if (status) {
      params.push(status);
      statusFilter = `AND b.status = $${params.length}`;
    }
    const { rows } = await pool.query(
      `SELECT b.*, s.name AS service_name, s.image_url,
              u.full_name AS coach_name
       FROM bookings b
       JOIN services s ON s.id = b.service_id
       LEFT JOIN coaches co ON co.id = b.coach_id
       LEFT JOIN users u ON u.id = co.user_id
       WHERE b.user_id = $1 ${statusFilter}
       ORDER BY b.starts_at DESC`,
      params
    );
    res.json(rows);
  } catch (err) {
    next(err);
  }
});

// GET /api/bookings/:id
router.get('/:id', requireAuth, async (req, res, next) => {
  try {
    const { rows } = await pool.query(
      `SELECT b.*, s.name AS service_name, u.full_name AS coach_name
       FROM bookings b
       JOIN services s ON s.id = b.service_id
       LEFT JOIN coaches co ON co.id = b.coach_id
       LEFT JOIN users u ON u.id = co.user_id
       WHERE b.id = $1 AND b.user_id = $2`,
      [req.params.id, req.user.sub]
    );
    if (!rows[0]) return res.status(404).json({ error: 'Booking not found' });
    res.json(rows[0]);
  } catch (err) {
    next(err);
  }
});

// POST /api/bookings
router.post('/', requireAuth, async (req, res, next) => {
  const client = await pool.connect();
  try {
    const { serviceId, coachId, startsAt, notes } = createSchema.parse(req.body);

    await client.query('BEGIN');

    // Lock the availability slot and confirm it's free
    const { rows: slots } = await client.query(
      `SELECT id, is_booked FROM availability
       WHERE coach_id = $1 AND service_id = $2 AND starts_at = $3
       FOR UPDATE`,
      [coachId, serviceId, startsAt]
    );
    if (!slots[0] || slots[0].is_booked) {
      await client.query('ROLLBACK');
      return res.status(409).json({ error: 'Time slot is no longer available' });
    }

    // Fetch service price
    const { rows: svcRows } = await client.query(
      'SELECT price_cents FROM services WHERE id = $1 AND active = TRUE',
      [serviceId]
    );
    if (!svcRows[0]) {
      await client.query('ROLLBACK');
      return res.status(404).json({ error: 'Service not found' });
    }

    // Create booking
    const { rows } = await client.query(
      `INSERT INTO bookings (id, ref, user_id, service_id, coach_id, starts_at, status, notes, price_cents)
       VALUES ($1,$2,$3,$4,$5,$6,'confirmed',$7,$8) RETURNING *`,
      [uuidv4(), makeRef(), req.user.sub, serviceId, coachId, startsAt, notes ?? null, svcRows[0].price_cents]
    );

    // Mark slot as booked
    await client.query(
      'UPDATE availability SET is_booked = TRUE WHERE id = $1',
      [slots[0].id]
    );

    await client.query('COMMIT');
    res.status(201).json(rows[0]);
  } catch (err) {
    await client.query('ROLLBACK');
    next(err);
  } finally {
    client.release();
  }
});

// PATCH /api/bookings/:id  (status update — staff/owner or cancel own)
router.patch('/:id', requireAuth, async (req, res, next) => {
  try {
    const { status } = updateSchema.parse(req.body);
    const isAdmin = ['owner', 'staff'].includes(req.user.role);

    // Customers can only cancel their own bookings
    const whereClause = isAdmin
      ? 'WHERE id = $2'
      : "WHERE id = $2 AND user_id = $1 AND $3 = 'cancelled'";
    const params = isAdmin ? [status, req.params.id] : [req.user.sub, req.params.id, status];

    const { rows } = await pool.query(
      `UPDATE bookings SET status = $1 ${whereClause} RETURNING *`,
      isAdmin ? [status, req.params.id] : [status, req.params.id]
    );

    if (!rows.length) return res.status(404).json({ error: 'Booking not found or action not allowed' });
    res.json(rows[0]);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
