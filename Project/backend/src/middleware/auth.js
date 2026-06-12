const { verify } = require('../config/jwt');
const { messages } = require('../constants/messages');

function requireAuth(req, res, next) {
  const header = req.headers.authorization;
  if (!header || !header.startsWith('Bearer ')) {
    return res.status(401).json({ error: messages.auth.missingOrInvalidToken });
  }
  try {
    req.user = verify(header.slice(7));
    next();
  } catch {
    res.status(401).json({ error: messages.auth.tokenExpiredOrInvalid });
  }
}

function requireRole(...roles) {
  return (req, res, next) => {
    if (!roles.includes(req.user?.role)) {
      return res.status(403).json({ error: messages.auth.forbidden });
    }
    next();
  };
}

module.exports = { requireAuth, requireRole };
