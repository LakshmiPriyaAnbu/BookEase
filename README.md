# BookEase — Service Booking Platform

BookEase is a full-stack fitness and wellness booking app. Customers browse services like personal training and nutrition coaching, pick a time slot, and book a session. Admins manage bookings and services from a dedicated dashboard.

The project has **four codebases in one folder** — a website, an iPhone app, an Android app, and a backend server. All three client apps work with **built-in mock data**, so you can run any of them and see a fully working UI without setting up a database.

---

## What's in this repo

```
BookEase/
├── Project/
│   ├── angular/        → Website (runs in a browser)
│   ├── backend/        → API server (Node.js + PostgreSQL)
│   └── mobile/
│       ├── iOS/        → iPhone app (Swift + SwiftUI)
│       └── android/    → Android app (Kotlin + Jetpack Compose)
└── design/             → Colour tokens and design reference
```

---

## How do the pieces fit together

```
Browser / iPhone / Android app
        │
        │  HTTP requests
        ▼
  Backend server (port 3000)
        │
        │  reads & writes
        ▼
  PostgreSQL database
```

Right now every client uses **mock data** built into the code, so you can run the website or either mobile app without a server. When you're ready to use real data, you start the backend and swap the mock calls for API calls (each platform README explains exactly how).

---

## Quickest way to see the app (2 minutes)

The website needs only Node.js — no database, no extra tools.

1. Install [Node.js LTS](https://nodejs.org) if you don't have it
2. Open a terminal and run:

```bash
cd path/to/BookEase/Project/angular
npm install
npm start
```

3. Open your browser and go to **http://localhost:4200**

That's it — you'll see the full BookEase website with mock data.

---

## All screens and what they do

### Customer screens (website + both mobile apps)

| Screen | Website URL | What you'll see |
|--------|-------------|-----------------|
| **Home / Landing** | `/` | Hero banner, featured services, how it works section, CTA to browse |
| **Services list** | `/services` | All available services with search bar and category filters |
| **Service detail** | `/services/:id` | Full description, duration, price, coach info, and Book button |
| **Booking form** | `/book?serviceId=svc-1` | Date picker, time slot selector, and contact details form |
| **Booking confirmation** | `/confirmation/:id` | Success message with booking reference number |
| **My bookings** | *(mobile tab "Bookings")* | List of upcoming and past bookings with status badges |
| **Profile** | *(mobile tab "Profile")* | User info and settings |
| **How It Works** | `/how-it-works` | Step-by-step explanation of the booking process |
| **Pricing** | `/pricing` | Service pricing tiers |
| **Contact** | `/contact` | Contact form |
| **Login** | `/login` | Email and password login form |
| **Not Found** | `/anything-invalid` | 404 page |

### Admin screens (website only)

| Screen | Website URL | What you'll see |
|--------|-------------|-----------------|
| **Admin dashboard** | `/admin` | Stats cards (total bookings, revenue, active services) and weekly chart |
| **Manage bookings** | `/admin/bookings` | Full list of all bookings; confirm, complete, or cancel each one |
| **Manage services** | `/admin/services` | List of services; add new ones or edit existing ones |

---

## How to test the full booking flow

This is the main user journey — go through it on the website to see how everything connects.

### Step 1 — Browse services
1. Go to **http://localhost:4200**
2. Click **"View All Services"** or navigate to **http://localhost:4200/services**
3. You'll see a grid of services (Personal Training, HIIT, Mobility, Nutrition Coaching, etc.)
4. Try the **search bar** — type "training" or "nutrition"
5. Try the **category filter chips** — click "Strength" or "Nutrition" to filter

### Step 2 — View a service detail
1. Click any service card
2. You'll see the full service page: description, duration, price, coach photo and bio
3. Click **"Book Now"** to go to the booking form

### Step 3 — Fill in the booking form
1. You're now at `/book?serviceId=svc-X`
2. **Pick a date** — click any date chip in the calendar row (dates are shown as scrollable chips)
3. **Pick a time slot** — click any available time (e.g. "10:00 AM", "2:00 PM")
   - Slots shown in green/active are available; grey ones are taken
4. **Fill in your details:**
   - Name: `Jane Smith`
   - Email: `jane@example.com`
   - Phone: `555-0100` (optional)
   - Notes: `First session, please start with basics` (optional)
5. Click **"Confirm Booking"**

### Step 4 — See the confirmation
- You'll be redirected to `/confirmation/:id`
- A green success banner appears with your booking reference (e.g. `BK-0042`)
- The page shows the service name, date, time, and duration

### Step 5 — Try the admin panel
1. Go to **http://localhost:4200/admin**
2. You'll see the dashboard with stats: total bookings, revenue, active services
3. Click **"Bookings"** in the sidebar → see all mock bookings
4. Click the action buttons: **Confirm**, **Complete**, or **Cancel** on any booking
5. Click **"Services"** in the sidebar → see all services, try adding or editing one

---

## How to test the mobile apps

### iOS (requires a Mac + Xcode)

The bottom navigation bar has four tabs:

| Tab | What to do |
|-----|------------|
| **Home** | Scroll down to see featured services and upcoming session |
| **Explore** | Browse and filter services; tap one to see details |
| **Bookings** | See your booking history and status (Pending, Confirmed, etc.) |
| **Profile** | View profile info and settings |

**Booking flow on iOS:**
1. Tap **Explore** tab
2. Tap any service card
3. On the service detail screen, tap **"Book Now"**
4. Pick a date from the horizontal date picker
5. Tap an available time slot
6. Fill in your name and email
7. Tap **"Confirm Booking"**
8. You'll see the confirmation screen

### Android (requires Android Studio)

Same four bottom tabs as iOS. The booking flow is identical — tap Explore, tap a service, tap "Book", pick date and time, fill the form, confirm.

---

## Run each platform

### Website

**Requirements:** Node.js (any recent LTS version)

```bash
cd Project/angular
npm install        # only needed once
npm start          # starts dev server
```

Opens at **http://localhost:4200**. Hot-reloads on file changes.

---

### Backend server

**Requirements:** Node.js + PostgreSQL

```bash
# 1 — install PostgreSQL from https://postgresql.org/download
# 2 — create the database
psql -U postgres
CREATE DATABASE bookease;
\q

# 3 — go to the backend folder
cd Project/backend

# 4 — install packages
npm install

# 5 — create your .env file
cp .env.example .env
# open .env in a text editor and set your PostgreSQL password

# 6 — create the database tables
npm run migrate

# 7 — start the server
npm run dev
```

Server runs at **http://localhost:3000**. Verify it's working:
```
http://localhost:3000/health   →   {"status":"ok"}
```

---

### iOS app

**Requirements:** Mac + Xcode 15 or later

1. Open Xcode
2. **File → Open** → select `Project/mobile/iOS/BookEase.xcodeproj`
3. In the toolbar, select **iPhone 15 Pro** as the simulator
4. Press **⌘ R** to build and run
5. The simulator opens with the app running

---

### Android app

**Requirements:** Android Studio (any recent version)

1. Open Android Studio
2. **File → Open** → select the `Project/mobile/android/` folder
3. Wait for **Gradle sync** to finish (progress bar at the bottom; takes a few minutes first time)
4. Click the green **▶ Run** button
5. Choose **Pixel 7 API 34** emulator (or create one if none listed)
6. The emulator opens with the app running

---

## Backend API reference

All endpoints are at `http://localhost:3000/api`

### Public (no login needed)

| Method | Endpoint | What it does |
|--------|----------|--------------|
| `GET` | `/categories` | List all service categories |
| `GET` | `/services` | List all services |
| `GET` | `/services/:id` | Get one service by ID |
| `GET` | `/availability?serviceId=X&date=YYYY-MM-DD` | Get available time slots |

### Requires login (send JWT in `Authorization: Bearer <token>` header)

| Method | Endpoint | What it does |
|--------|----------|--------------|
| `POST` | `/auth/register` | Create a new account |
| `POST` | `/auth/login` | Log in, receive a JWT token |
| `GET` | `/me` | Get your profile |
| `POST` | `/bookings` | Create a new booking |
| `GET` | `/bookings` | List your bookings |
| `PATCH` | `/bookings/:id` | Update booking status |

### Admin only

| Method | Endpoint | What it does |
|--------|----------|--------------|
| `GET` | `/admin/stats` | Dashboard stats |
| `GET` | `/admin/analytics/weekly` | Weekly booking chart data |

### Test the API with curl

```bash
# Register a new user
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Smith","email":"jane@example.com","password":"password123"}'

# Log in (copy the token from the response)
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"jane@example.com","password":"password123"}'

# List services (no token needed)
curl http://localhost:3000/api/services

# Make a booking (replace TOKEN with the token from login)
curl -X POST http://localhost:3000/api/bookings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"serviceId":"svc-1","slotId":"slot-1","notes":"First session"}'
```

---

## Mock data reference

All client apps use the same mock data so you can explore every screen without the backend.

| ID | Service name | Category | Price |
|----|-------------|----------|-------|
| `svc-1` | Power Strength Training | Strength | $65 |
| `svc-2` | HIIT Cardio Blast | HIIT | $55 |
| `svc-3` | Mobility & Flexibility | Mobility | $45 |
| `svc-4` | Nutrition Coaching | Nutrition | $75 |

Use `svc-1` through `svc-4` in URLs like `/services/svc-1` or `/book?serviceId=svc-2`.

---

## Tech stack

| Platform | Language | Key libraries |
|----------|----------|---------------|
| Website | TypeScript | Angular 17, RxJS, SCSS |
| Backend | JavaScript | Node.js, Express, PostgreSQL, JWT |
| iPhone app | Swift | SwiftUI, Combine |
| Android app | Kotlin | Jetpack Compose, Material 3, Hilt |

---

## Design system

All platforms share the same visual language:

- **Primary colour** — Purple `#5B4BE3`
- **Accent colour** — Lime `#C8F25C` (used on dark backgrounds)
- **Heading font** — Space Grotesk
- **Body font** — Plus Jakarta Sans

Design tokens live in `design/bookease-tokens.css` and are mirrored in each platform's theme file.

---

## Common problems

### Website

**`npm: command not found`**
→ Install Node.js from https://nodejs.org

**Page is blank or shows an error**
→ Check the terminal — the error message will say exactly what's wrong

**Port 4200 already in use**
→ Stop whatever is running on that port, or run `npm start -- --port 4201`

### Backend

**`password authentication failed for user postgres`**
→ The password in `.env` doesn't match what you set when installing PostgreSQL

**`database "bookease" does not exist`**
→ Run the `CREATE DATABASE bookease;` step in psql

**Port 3000 already in use**
→ Change `PORT=3000` to `PORT=3001` in your `.env` file

### iOS

**Build fails with "No account for team"**
→ Xcode → Settings → Accounts → add your Apple ID → set team in project settings

**"Xcode can't find the iOS 17 simulator"**
→ Xcode → Settings → Platforms → download iOS 17 Simulator

### Android

**Gradle sync failed**
→ Check your internet connection, then try **File → Sync Project with Gradle Files**

**No emulator in the device list**
→ Open **Device Manager** (right panel) → Create Device → Pixel 7 → API 34 → Finish

**App crashes immediately**
→ Open **Logcat** (bottom panel) and look for red lines — they'll say exactly what failed
