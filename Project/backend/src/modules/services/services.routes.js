const router = require('express').Router();
const { z } = require('zod');
const { v4: uuidv4 } = require('uuid');
const pool = require('../../config/db');
const { requireAuth, requireRole } = require('../../middleware/auth');

const serviceSchema = z.object({
  name: z.string().min(2),
  description: z.string().optional(),
  priceCents: z.number().int().nonnegative(),
  durationMin: z.number().int().positive(),
  categoryId: z.string().uuid().optional(),
  imageUrl: z.string().url().optional(),
});

// GET /api/services
router.get('/', async (req, res, next) => {
  try {
    const { search, categoryId, page = '1', limit = '20' } = req.query;
    const offset = (parseInt(page) - 1) * parseInt(limit);
    const params = [];
    const conditions = ['s.active = TRUE'];

    if (search) {
      params.push(`%${search}%`);
      conditions.push(`s.name ILIKE $${params.length}`);
    }
    if (categoryId) {
      params.push(categoryId);
      conditions.push(`s.category_id = $${params.length}`);
    }

    const where = conditions.length ? `WHERE ${conditions.join(' AND ')}` : '';
    params.push(parseInt(limit), offset);

    const { rows } = await pool.query(
      `SELECT s.*, c.name AS category_name,
              u.full_name AS coach_name, co.id AS coach_id
       FROM services s
       LEFT JOIN categories c ON s.category_id = c.id
       LEFT JOIN availability a ON a.service_id = s.id AND a.is_booked = FALSE
       LEFT JOIN coaches co ON co.id = a.coach_id
       LEFT JOIN users u ON u.id = co.user_id
       ${where}
       GROUP BY s.id, c.name, u.full_name, co.id
       ORDER BY s.created_at DESC
       LIMIT $${params.length - 1} OFFSET $${params.length}`,
      params
    );
    res.json(rows);
  } catch (err) {
    next(err);
  }
});

// GET /api/services/:id
router.get('/:id', async (req, res, next) => {
  try {
    const { rows } = await pool.query(
      `SELECT s.*, c.name AS category_name
       FROM services s
       LEFT JOIN categories c ON s.category_id = c.id
       WHERE s.id = $1`,
      [req.params.id]
    );
    if (!rows[0]) return res.status(404).json({ error: 'Service not found' });
    res.json(rows[0]);
  } catch (err) {
    next(err);
  }
});

// POST /api/services  (admin/owner only)
router.post('/', requireAuth, requireRole('owner', 'staff'), async (req, res, next) => {
  try {
    const { name, description, priceCents, durationMin, categoryId, imageUrl } =
      serviceSchema.parse(req.body);
    const { rows } = await pool.query(
      `INSERT INTO services (id, name, description, price_cents, duration_min, category_id, image_url)
       VALUES ($1,$2,$3,$4,$5,$6,$7) RETURNING *`,
      [uuidv4(), name, description, priceCents, durationMin, categoryId ?? null, imageUrl ?? null]
    );
    res.status(201).json(rows[0]);
  } catch (err) {
    next(err);
  }
});

// PUT /api/services/:id
router.put('/:id', requireAuth, requireRole('owner', 'staff'), async (req, res, next) => {
  try {
    const data = serviceSchema.partial().parse(req.body);
    const fields = Object.keys(data);
    if (!fields.length) return res.status(400).json({ error: 'Nothing to update' });

    const colMap = {
      name: 'name', description: 'description', priceCents: 'price_cents',
      durationMin: 'duration_min', categoryId: 'category_id', imageUrl: 'image_url',
    };
    const setClauses = fields.map((f, i) => `${colMap[f]} = $${i + 1}`).join(', ');
    const values = fields.map((f) => data[f]);
    values.push(req.params.id);

    const { rows } = await pool.query(
      `UPDATE services SET ${setClauses} WHERE id = $${values.length} RETURNING *`,
      values
    );
    if (!rows[0]) return res.status(404).json({ error: 'Service not found' });
    res.json(rows[0]);
  } catch (err) {
    next(err);
  }
});

// DELETE /api/services/:id
router.delete('/:id', requireAuth, requireRole('owner'), async (req, res, next) => {
  try {
    await pool.query('UPDATE services SET active = FALSE WHERE id = $1', [req.params.id]);
    res.status(204).send();
  } catch (err) {
    next(err);
  }
});

module.exports = router;
