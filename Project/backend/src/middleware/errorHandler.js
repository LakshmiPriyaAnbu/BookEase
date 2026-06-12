const { ZodError } = require('zod');
const { messages } = require('../constants/messages');

function errorHandler(err, req, res, next) {
  if (err instanceof ZodError) {
    return res.status(400).json({ error: messages.errors.validationError, issues: err.issues });
  }
  console.error(err);
  res.status(err.status || 500).json({ error: err.message || messages.errors.internalServerError });
}

module.exports = errorHandler;
