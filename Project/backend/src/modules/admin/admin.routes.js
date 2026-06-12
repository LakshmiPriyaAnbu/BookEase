const router = require('express').Router();
const pool = require('../../config/db');
const { requireAuth, requireRole } = require('../../middleware/auth');

// All admin routes require owner or staff role
router.use(requireAuth, requireRole('owner', 'staff'));

// GET /api/admin/stats — dashboard KPIs
router.get('/stats', async (req, res, next) => {
  try {
    const [bookings, revenue, users, services] = await Promise.all([
      pool.query("SELECT COUNT(*) FROM bookings WHERE status != 'cancelled'"),
      pool.query("SELECT COALESCE(SUM(price_cents),0) AS total FROM bookings WHERE status = 'completed'"),
      pool.query('SELECT COUNT(*) FROM users WHERE role = $1', ['customer']),
      pool.query('SELECT COUNT(*) FROM services WHERE active = TRUE'),
    ]);
    res.json({
      totalBookings: parseInt(bookings.rows[0].count),
      totalRevenueCents: parseInt(revenue.rows[0].total),
      totalCustomers: parseInt(users.rows[0].count),
      activeServices: parseInt(services.rows[0].count),
    });
  } catch (err) {
    next(err);
  }
});

// GET /api/admin/analytics/weekly — bookings per day for last 7 days
router.get('/analytics/weekly', async (req, res, next) => {
  try {
    const { rows } = await pool.query(
      `SELECT DATE(starts_at) AS day, COUNT(*) AS bookings,
              COALESCE(SUM(price_cents),0) AS revenue_cents
       FROM bookings
       WHERE starts_at >= NOW() - INTERVAL '7 days'
         AND status != 'cancelled'
       GROUP BY DATE(starts_at)
       ORDER BY day`
    );
    res.json(rows);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
