const router = require('express').Router();
const pool = require('../../config/db');

// GET /api/categories
router.get('/', async (req, res, next) => {
  try {
    const { rows } = await pool.query(
      'SELECT * FROM categories ORDER BY sort_order, name'
    );
    res.json(rows);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
