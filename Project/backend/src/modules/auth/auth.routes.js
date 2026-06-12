const router = require('express').Router();
const { z } = require('zod');
const bcrypt = require('bcryptjs');
const { v4: uuidv4 } = require('uuid');
const pool = require('../../config/db');
const { sign } = require('../../config/jwt');
const { requireAuth } = require('../../middleware/auth');
const { messages } = require('../../constants/messages');

const registerSchema = z.object({
  fullName: z.string().min(2),
  email: z.string().email(),
  phone: z.string().optional(),
  password: z.string().min(8),
});

const loginSchema = z.object({
  email: z.string().email(),
  password: z.string(),
});

// POST /api/auth/register
router.post('/register', async (req, res, next) => {
  try {
    const { fullName, email, phone, password } = registerSchema.parse(req.body);
    const hash = await bcrypt.hash(password, 12);
    const { rows } = await pool.query(
      `INSERT INTO users (id, full_name, email, phone, password, role)
       VALUES ($1, $2, $3, $4, $5, 'customer')
       RETURNING id, full_name, email, phone, role, created_at`,
      [uuidv4(), fullName, email, phone ?? null, hash]
    );
    const user = rows[0];
    const token = sign({ sub: user.id, role: user.role });
    res.status(201).json({ token, user });
  } catch (err) {
    if (err.code === '23505') return res.status(409).json({ error: messages.auth.emailAlreadyRegistered });
    next(err);
  }
});

// POST /api/auth/login
router.post('/login', async (req, res, next) => {
  try {
    const { email, password } = loginSchema.parse(req.body);
    const { rows } = await pool.query(
      'SELECT * FROM users WHERE email = $1',
      [email]
    );
    const user = rows[0];
    if (!user || !(await bcrypt.compare(password, user.password))) {
      return res.status(401).json({ error: messages.auth.invalidCredentials });
    }
    const token = sign({ sub: user.id, role: user.role });
    const { password: _, ...safeUser } = user;
    res.json({ token, user: safeUser });
  } catch (err) {
    next(err);
  }
});

// POST /api/auth/logout  (client just discards the token, but we acknowledge)
router.post('/logout', requireAuth, (req, res) => {
  res.json({ message: messages.auth.loggedOut });
});

module.exports = router;
