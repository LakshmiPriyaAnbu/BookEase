# BookEase — Claude Code Guide

BookEase is a multi-platform service booking app with four codebases in one repo.

## Repository layout

```
Project/
  mobile/
    iOS/        Swift 5.10 / SwiftUI / MVVM / iOS 17+
    android/    Kotlin / Jetpack Compose / MVVM+UDF / Hilt / minSdk 26
  angular/      Angular 17+ / Standalone components / Signals / TypeScript strict
  backend/      Node.js / Express / CommonJS / modular architecture
```

## Agents — always use the right one

| Task | Agent |
|------|-------|
| iOS Swift/SwiftUI work | `ios-agent` |
| Android Kotlin/Compose work | `android-agent` |
| Angular TypeScript work | `angular-agent` |
| Node.js/Express API work | `backend-agent` |

Spawn the matching agent rather than writing platform code directly. Agents know the project conventions, directory structure, and architecture rules for their platform.

## Skills

- `/commit` — stage and commit changes with a well-formed message
- `/worklog` — generate a plain-text summary of work from git history

## Cross-platform conventions

- Domain models must stay consistent across platforms: if a field is added to the backend, it must be reflected in iOS `Models/`, Android `Models.kt`, and Angular `core/models/`.
- API base URL lives in environment config on every platform — never hardcoded.
- Booking status enum values must match exactly: `pending`, `confirmed`, `cancelled`.

## What NOT to do

- Do not write business logic in route handlers (backend), Views (iOS), Composables (Android), or templates (Angular).
- Do not hardcode colors, fonts, or spacing outside theme/design-system files.
- Do not commit secrets, API keys, or `.env` files.
- Do not use `git add -A` — stage specific files only.
