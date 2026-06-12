/**
 * Centralized string constants for all API response messages, error strings,
 * and validation messages used across route handlers, middleware, and controllers.
 *
 * Usage:
 *   const { messages } = require('../../constants/messages');
 *   res.json({ error: messages.auth.invalidCredentials });
 */

const messages = {
  auth: {
    emailAlreadyRegistered: 'Email already registered',
    invalidCredentials: 'Invalid credentials',
    loggedOut: 'Logged out',
    missingOrInvalidToken: 'Missing or invalid token',
    tokenExpiredOrInvalid: 'Token expired or invalid',
    forbidden: 'Forbidden',
  },

  user: {
    notFound: 'User not found',
    preferencesUpdated: 'Preferences updated',
  },

  service: {
    notFound: 'Service not found',
    nothingToUpdate: 'Nothing to update',
  },

  availability: {
    missingParams: 'serviceId and date are required',
  },

  booking: {
    notFound: 'Booking not found',
    notFoundOrNotAllowed: 'Booking not found or action not allowed',
    slotUnavailable: 'Time slot is no longer available',
  },

  errors: {
    routeNotFound: 'Route not found',
    validationError: 'Validation error',
    internalServerError: 'Internal server error',
  },

  health: {
    ok: 'ok',
  },
};

module.exports = { messages };
