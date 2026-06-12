# BookEase — Android App

This is the Android app for BookEase. It's built with **Jetpack Compose** — Google's modern way to build Android apps. It comes with mock data so you can run it straight away without needing the backend server.

---

## Before you start — what you need

You need **Android Studio** installed. It works on Mac, Windows, and Linux.

### Install Android Studio
1. Go to https://developer.android.com/studio
2. Click **Download Android Studio** and run the installer
3. Follow the setup wizard — it will automatically install the Android SDK and an emulator
4. When asked to install a virtual device, choose **Pixel 7** with **API 34**

**Minimum versions:**
- Android Studio Hedgehog (2023.1.1) or later
- The app runs on Android 8.0+ (API level 26+)

---

## How to run the app

1. Open **Android Studio**
2. Choose **File → Open…** from the menu bar
3. Navigate to `BookEase/Project/mobile/android/` and click **OK**
4. Wait for Gradle to sync — you'll see a progress bar at the bottom. This can take a few minutes the first time
5. Once syncing is done, click the **▶ Run** button (green triangle) at the top
6. Choose your emulator (e.g. "Pixel 7 API 34") and click **OK**
7. The emulator will open and show the BookEase app!

---

## If Gradle sync fails

This sometimes happens on first open. Try:
1. **File → Sync Project with Gradle Files**
2. If it still fails: **File → Invalidate Caches → Invalidate and Restart**

---

## Screens in the app

| Screen | How to get there |
|--------|-----------------|
| **Home** | Opens automatically — shows upcoming session, popular services, categories |
| **Explore** | Tap "Explore" in the bottom navigation bar |
| **Service detail** | Tap any service card |
| **Booking** | Tap "Book" on a service detail page |
| **Confirmation** | Shown after completing a booking |
| **My bookings** | Tap "Bookings" in the bottom navigation bar |
| **Profile** | Tap "Profile" in the bottom navigation bar |

---

## Folder layout

```
app/src/main/java/com/bookease/app/
├── MainActivity.kt              ← The starting point of the app
├── navigation/
│   └── Navigation.kt            ← Defines which screen shows at which route
├── data/
│   ├── models/Models.kt         ← Data structures (Service, Booking, User…)
│   └── mock/MockData.kt         ← The fake data shown while backend isn't connected
└── ui/
    ├── components/Components.kt  ← Reusable UI pieces (cards, badges, buttons…)
    └── screens/                  ← One folder per screen
        ├── home/
        ├── explore/
        ├── detail/
        ├── booking/
        ├── confirmation/
        ├── bookings/
        └── profile/
```

---

## Connecting to the real backend

Right now the app shows fake data from `MockData.kt`. When you're ready to connect to real data:

1. Start the backend server (see `../../backend/README.md`)
2. Add Retrofit to `app/build.gradle.kts`:
   ```kotlin
   implementation("com.squareup.retrofit2:retrofit:2.9.0")
   implementation("com.squareup.retrofit2:converter-gson:2.9.0")
   ```
3. The emulator accesses your computer's localhost at IP `10.0.2.2` — so your base URL would be:
   ```
   http://10.0.2.2:3000/api
   ```

---

## Common problems

**"Gradle sync failed"**
→ Make sure you have an internet connection (Gradle downloads packages on first run). Then try **File → Sync Project with Gradle Files**.

**No emulator in the list**
→ Go to **Device Manager** (right side panel) → **Create Device** → choose Pixel 7 → Next → download API 34 if needed → Finish.

**The app installs but immediately crashes**
→ Open **Logcat** (bottom panel in Android Studio) and look for lines in red — they'll tell you exactly what went wrong.

**Build error: "Kotlin version incompatible"**
→ Go to **File → Project Structure → Project** and update the Kotlin version to match what's installed in your Android Studio.
