const router = require('express').Router();
const { z } = require('zod');
const pool = require('../../config/db');
const { requireAuth } = require('../../middleware/auth');
const { messages } = require('../../constants/messages');

const prefsSchema = z.object({
  notifications: z.boolean().optional(),
  avatarUrl: z.string().url().optional(),
});

// GET /api/me
router.get('/', requireAuth, async (req, res, next) => {
  try {
    const { rows } = await pool.query(
      'SELECT id, full_name, email, phone, role, avatar_url, created_at FROM users WHERE id = $1',
      [req.user.sub]
    );
    if (!rows[0]) return res.status(404).json({ error: messages.user.notFound });
    res.json(rows[0]);
  } catch (err) {
    next(err);
  }
});

// PATCH /api/me/preferences
router.patch('/preferences', requireAuth, async (req, res, next) => {
  try {
    const { avatarUrl } = prefsSchema.parse(req.body);
    if (avatarUrl !== undefined) {
      await pool.query('UPDATE users SET avatar_url = $1 WHERE id = $2', [avatarUrl, req.user.sub]);
    }
    res.json({ updated: true });
  } catch (err) {
    next(err);
  }
});

module.exports = router;
