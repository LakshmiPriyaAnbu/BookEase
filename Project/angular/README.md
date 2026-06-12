# BookEase — Web App

This is the customer-facing website and admin dashboard for BookEase. It's built with **Angular 17** and runs in any modern browser. It comes with mock data, so you don't need the backend running to see it work.

---

## Before you start — what you need

You need **Node.js** installed on your computer. That's it.

- Download Node.js from https://nodejs.org (choose the **LTS** version)
- Run the installer and follow the steps
- To check it worked, open your terminal and type `node --version` — you should see something like `v20.x.x`

---

## How to run the website

Open your terminal (Mac: Spotlight → "Terminal"; Windows: Start → "Command Prompt"):

```bash
# Step 1 — go into the angular folder
cd path/to/BookEase/Project/angular

# Step 2 — install the packages the app needs (only needed once)
npm install

# Step 3 — start the dev server
npm start
```

After a few seconds you'll see:
```
✔ Compiled successfully.
Local:   http://localhost:4200/
```

Open your browser and visit **http://localhost:4200** — the app is running!

> To stop the server, press `Ctrl + C` in the terminal.

---

## Pages you can visit

Once the app is running, try these URLs in your browser:

| URL | What you'll see |
|-----|-----------------|
| `http://localhost:4200` | Homepage — hero, popular services, how it works |
| `http://localhost:4200/services` | Browse all services with search and filters |
| `http://localhost:4200/services/svc-1` | Detail page for one service |
| `http://localhost:4200/book?serviceId=svc-1` | Booking form |
| `http://localhost:4200/admin` | Admin dashboard |
| `http://localhost:4200/admin/bookings` | Manage all bookings |
| `http://localhost:4200/admin/services` | Add / edit services |

---

## Folder layout (what's inside `src/`)

You don't need to understand all of this to get started — but here's a map if you're curious:

```
src/
├── app/
│   ├── app.routes.ts          ← Defines which component shows at which URL
│   ├── core/services/         ← Files that fetch data (currently using mock data)
│   ├── shared/
│   │   ├── components/        ← Reusable UI pieces (buttons, cards, badges…)
│   │   ├── models/models.ts   ← TypeScript types + all the mock data
│   │   └── pipes/             ← Small helpers (e.g. formats "$65" from 6500 cents)
│   └── features/              ← One folder per page/screen
│       ├── landing/           ← Homepage
│       ├── services/          ← Services list + detail page
│       ├── booking/           ← Booking form
│       ├── confirmation/      ← Success page after booking
│       └── admin/             ← Admin dashboard, bookings, service management
└── styles/
    ├── _tokens.scss           ← All colours, spacing, fonts as variables
    └── main.scss              ← Global styles applied to every page
```

---

## Connecting to the real backend

The app currently shows **fake/mock data**. When you're ready to connect to the real server:

1. Start the backend (see [../backend/README.md](../backend/README.md))
2. Open `src/app/core/services/services.service.ts`
3. Find lines that say `return of(MOCK_SERVICES)` and replace them with `return this.api.get('/services')`

That's the only change needed — the rest of the app will automatically use real data.

---

## Building for production

When you want to deploy the app to the internet:

```bash
npm run build
```

This creates a `dist/` folder with the final files. Upload that folder to any web hosting service (Netlify, Vercel, GitHub Pages, etc.).

---

## Common problems

**"npm: command not found"**
→ Node.js isn't installed. Go to https://nodejs.org and install it.

**"Error: Cannot find module …"**
→ You skipped `npm install`. Run it again.

**The page is blank or shows an error**
→ Check the terminal window — there's usually a clear error message explaining what went wrong.
