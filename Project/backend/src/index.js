require('dotenv').config();
const express = require('express');
const cors = require('cors');
const errorHandler = require('./middleware/errorHandler');

const authRoutes         = require('./modules/auth/auth.routes');
const categoriesRoutes   = require('./modules/categories/categories.routes');
const servicesRoutes     = require('./modules/services/services.routes');
const availabilityRoutes = require('./modules/availability/availability.routes');
const bookingsRoutes     = require('./modules/bookings/bookings.routes');
const usersRoutes        = require('./modules/users/users.routes');
const adminRoutes        = require('./modules/admin/admin.routes');

const app = express();

app.use(cors());
app.use(express.json());

// Health check
app.get('/health', (req, res) => res.json({ status: 'ok' }));

// API routes
app.use('/api/auth',         authRoutes);
app.use('/api/categories',   categoriesRoutes);
app.use('/api/services',     servicesRoutes);
app.use('/api/availability', availabilityRoutes);
app.use('/api/bookings',     bookingsRoutes);
app.use('/api/me',           usersRoutes);
app.use('/api/admin',        adminRoutes);

// 404
app.use((req, res) => res.status(404).json({ error: 'Route not found' }));

// Error handler
app.use(errorHandler);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`BookEase API listening on port ${PORT}`));

module.exports = app;
