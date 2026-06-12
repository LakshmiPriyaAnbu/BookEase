const router = require('express').Router();
const pool = require('../../config/db');
const { messages } = require('../../constants/messages');

// GET /api/availability?serviceId=&date=YYYY-MM-DD
router.get('/', async (req, res, next) => {
  try {
    const { serviceId, date } = req.query;
    if (!serviceId || !date) {
      return res.status(400).json({ error: messages.availability.missingParams });
    }
    const { rows } = await pool.query(
      `SELECT a.id, a.starts_at, a.is_booked,
              co.id AS coach_id, u.full_name AS coach_name
       FROM availability a
       JOIN coaches co ON co.id = a.coach_id
       JOIN users u ON u.id = co.user_id
       WHERE a.service_id = $1
         AND a.starts_at::date = $2::date
         AND a.is_booked = FALSE
       ORDER BY a.starts_at`,
      [serviceId, date]
    );
    res.json(rows);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
