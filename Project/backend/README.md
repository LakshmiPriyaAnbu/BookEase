# BookEase — Backend API

This is the server that powers BookEase. It's built with **Express.js** (a Node.js web framework) and stores data in a **PostgreSQL** database. It handles user login, services, bookings, and admin statistics.

> The website and mobile apps work without this running (they use mock data). You only need this when you're ready to use real data.

---

## Before you start — what you need

You need two things installed:

### 1. Node.js
- Download from https://nodejs.org (choose the **LTS** version)
- Run the installer
- Check it worked: open a terminal and type `node --version`

### 2. PostgreSQL
- Download from https://www.postgresql.org/download
- Run the installer (remember the password you set for the `postgres` user — you'll need it)
- Check it worked: open a terminal and type `psql --version`

---

## Setup — step by step

### Step 1 — go to the backend folder

```bash
cd path/to/BookEase/Project/backend
```

### Step 2 — install packages

```bash
npm install
```

### Step 3 — create the database

Open a terminal and type:

```bash
psql -U postgres
```

Enter your PostgreSQL password when asked. Then type this SQL command to create the database:

```sql
CREATE DATABASE bookease;
\q
```

### Step 4 — create your `.env` file

The `.env` file is where you keep private settings (like your database password). We never commit this to git.

Copy the example file:

```bash
cp .env.example .env
```

Now open `.env` in any text editor and fill in your details:

```
PORT=3000
DATABASE_URL=postgresql://postgres:YOUR_PASSWORD_HERE@localhost:5432/bookease
JWT_SECRET=any-long-random-string-you-make-up
JWT_EXPIRES_IN=7d
```

Replace `YOUR_PASSWORD_HERE` with the password you set when installing PostgreSQL.

### Step 5 — create the database tables

```bash
npm run migrate
```

You should see: `Migration complete.`

### Step 6 — start the server

```bash
npm run dev
```

You should see: `BookEase API listening on port 3000`

The API is now running at **http://localhost:3000**

---

## Testing that it works

Open your browser and go to:
```
http://localhost:3000/health
```

You should see: `{"status":"ok"}`

---

## API endpoints (what the server can do)

All URLs start with `http://localhost:3000/api`

### Anyone can use these (no login needed)
| What it does | URL |
|---|---|
| List all categories | `GET /api/categories` |
| List all services | `GET /api/services` |
| Get one service | `GET /api/services/:id` |
| See available time slots | `GET /api/availability?serviceId=X&date=YYYY-MM-DD` |

### These need you to be logged in
| What it does | URL |
|---|---|
| Create an account | `POST /api/auth/register` |
| Log in | `POST /api/auth/login` |
| View your profile | `GET /api/me` |
| Make a booking | `POST /api/bookings` |
| See your bookings | `GET /api/bookings` |
| Cancel a booking | `PATCH /api/bookings/:id` |

### Admin only
| What it does | URL |
|---|---|
| Dashboard stats | `GET /api/admin/stats` |
| Weekly chart data | `GET /api/admin/analytics/weekly` |

---

## Folder layout

```
src/
├── index.js           ← Starts the server, connects all routes
├── config/
│   ├── db.js          ← Connects to PostgreSQL
│   └── jwt.js         ← Creates and checks login tokens
├── middleware/
│   ├── auth.js        ← Checks if a request is logged in
│   └── errorHandler.js ← Handles errors in a consistent way
├── db/
│   └── migrations/
│       └── 001_initial_schema.sql  ← Creates all database tables
└── modules/
    ├── auth/          ← Login and register
    ├── categories/    ← Service categories
    ├── services/      ← CRUD for services
    ├── availability/  ← Available time slots
    ├── bookings/      ← Create and manage bookings
    ├── users/         ← User profile
    └── admin/         ← Admin stats and analytics
```

---

## Common problems

**"password authentication failed for user postgres"**
→ The password in your `.env` file is wrong. Double-check it matches what you set when installing PostgreSQL.

**"database bookease does not exist"**
→ You need to create the database. Follow Step 3 above.

**"Error: Cannot find module …"**
→ You skipped `npm install`. Run it.

**Port 3000 already in use**
→ Change `PORT=3000` in your `.env` to `PORT=3001` (or any other number) and restart.
