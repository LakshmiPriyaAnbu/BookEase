-- BookEase initial schema
-- Run: psql $DATABASE_URL -f src/db/migrations/001_initial_schema.sql

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Users
CREATE TABLE IF NOT EXISTS users (
  id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  full_name   TEXT NOT NULL,
  email       TEXT UNIQUE NOT NULL,
  phone       TEXT,
  password    TEXT NOT NULL,
  role        TEXT NOT NULL DEFAULT 'customer' CHECK (role IN ('customer', 'staff', 'owner')),
  avatar_url  TEXT,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Categories
CREATE TABLE IF NOT EXISTS categories (
  id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  name        TEXT UNIQUE NOT NULL,
  slug        TEXT UNIQUE NOT NULL,
  icon        TEXT,
  sort_order  INT NOT NULL DEFAULT 0
);

-- Services
CREATE TABLE IF NOT EXISTS services (
  id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  name            TEXT NOT NULL,
  description     TEXT,
  price_cents     INT NOT NULL CHECK (price_cents >= 0),
  duration_min    INT NOT NULL CHECK (duration_min > 0),
  category_id     UUID REFERENCES categories(id) ON DELETE SET NULL,
  image_url       TEXT,
  rating          NUMERIC(3,2),
  active          BOOLEAN NOT NULL DEFAULT TRUE,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Coaches (staff linked to users)
CREATE TABLE IF NOT EXISTS coaches (
  id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id     UUID UNIQUE REFERENCES users(id) ON DELETE CASCADE,
  bio         TEXT,
  specialties TEXT[],
  avatar_url  TEXT
);

-- Availability slots
CREATE TABLE IF NOT EXISTS availability (
  id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  coach_id    UUID REFERENCES coaches(id) ON DELETE CASCADE,
  service_id  UUID REFERENCES services(id) ON DELETE CASCADE,
  starts_at   TIMESTAMPTZ NOT NULL,
  is_booked   BOOLEAN NOT NULL DEFAULT FALSE,
  UNIQUE (coach_id, starts_at)
);

-- Bookings
CREATE TABLE IF NOT EXISTS bookings (
  id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  ref         TEXT UNIQUE NOT NULL,
  user_id     UUID REFERENCES users(id) ON DELETE CASCADE,
  service_id  UUID REFERENCES services(id) ON DELETE SET NULL,
  coach_id    UUID REFERENCES coaches(id) ON DELETE SET NULL,
  starts_at   TIMESTAMPTZ NOT NULL,
  status      TEXT NOT NULL DEFAULT 'pending'
                CHECK (status IN ('pending','confirmed','completed','cancelled')),
  notes       TEXT,
  price_cents INT NOT NULL,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Reviews
CREATE TABLE IF NOT EXISTS reviews (
  id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  booking_id  UUID UNIQUE REFERENCES bookings(id) ON DELETE CASCADE,
  service_id  UUID REFERENCES services(id) ON DELETE CASCADE,
  user_id     UUID REFERENCES users(id) ON DELETE CASCADE,
  rating      INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
  comment     TEXT,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Indexes for common queries
CREATE INDEX IF NOT EXISTS idx_bookings_user_id    ON bookings(user_id);
CREATE INDEX IF NOT EXISTS idx_bookings_status     ON bookings(status);
CREATE INDEX IF NOT EXISTS idx_bookings_starts_at  ON bookings(starts_at);
CREATE INDEX IF NOT EXISTS idx_availability_slot   ON availability(coach_id, service_id, starts_at);
CREATE INDEX IF NOT EXISTS idx_services_category   ON services(category_id);
