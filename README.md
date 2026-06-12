# BookEase — Service Booking Platform

BookEase is a full-stack app that lets people browse fitness and wellness sessions, pick a time slot, and book a session with a coach. Think of it like an online booking system for personal trainers, nutritionists, and wellness coaches.

---

## What's in this repo?

This is a **monorepo** — one folder that contains four separate projects that all work together:

```
BookEase/
├── Project/
│   ├── angular/        → The website (what users open in a browser)
│   ├── backend/        → The server (handles data, login, bookings)
│   └── mobile/
│       ├── iOS/        → iPhone app (built with SwiftUI)
│       └── android/    → Android app (built with Jetpack Compose)
└── design/             → Design files, colour tokens, screen mockups
```

Each project has its own `README.md` with full setup instructions. Start with the one you want to run.

---

## How do the pieces fit together?

```
Browser / iPhone / Android
        ↓  (API calls over HTTP)
   Backend server
        ↓  (reads & writes)
   PostgreSQL database
```

All three clients (website, iOS, Android) talk to the **same backend API**. Right now each app ships with **mock data** so you can run it and see the UI without needing a database at all.

---

## Quick links

| What you want to do | Where to go |
|---------------------|-------------|
| Run the website | [Project/angular/README.md](Project/angular/README.md) |
| Run the server | [Project/backend/README.md](Project/backend/README.md) |
| Open the iPhone app | [Project/mobile/iOS/README.md](Project/mobile/iOS/README.md) |
| Open the Android app | [Project/mobile/android/README.md](Project/mobile/android/README.md) |

---

## Absolute beginner? Start here

**To see the website (easiest start):**
1. Install [Node.js](https://nodejs.org) — download the LTS version and run the installer
2. Open your terminal (Mac: search "Terminal"; Windows: search "Command Prompt")
3. Type these commands one at a time, pressing Enter after each:
   ```
   cd path/to/BookEase/Project/angular
   npm install
   npm start
   ```
4. Open your browser and go to `http://localhost:4200` — you'll see the BookEase homepage!

**To also run the server (optional, needed for real data):**
- You'll also need [PostgreSQL](https://www.postgresql.org/download/) installed
- See [Project/backend/README.md](Project/backend/README.md) for the full steps

---

## What does the app do?

As a **customer** you can:
- Browse available fitness and wellness services
- Filter by category (Strength, HIIT, Mobility, Nutrition)
- View service details and coach info
- Pick a date and time slot and book a session
- See a booking confirmation with your booking reference
- View your upcoming and past bookings in your profile

As an **admin** you can:
- See a dashboard with total bookings and revenue stats
- View and manage all bookings (confirm, complete, cancel)
- Add, edit, and remove services

---

## Tech used

| Project | Technologies |
|---------|-------------|
| Website | Angular 17, TypeScript, SCSS |
| Server | Node.js, Express.js, PostgreSQL |
| iPhone app | Swift, SwiftUI |
| Android app | Kotlin, Jetpack Compose |

Don't worry if you don't know all of these — each README explains exactly what you need to install.

---

## Design

The app uses a consistent colour scheme and font across all platforms:

- **Purple** `#5B4BE3` — buttons, links, active states
- **Lime** `#C8F25C` — accent / CTA on dark backgrounds
- **Fonts** — Space Grotesk (headings) and Plus Jakarta Sans (body text)

All the exact colours, sizes, and spacing rules live in `design/bookease-tokens.css`.
