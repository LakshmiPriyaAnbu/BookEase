# BookEase — iOS App

This is the iPhone app for BookEase. It's built with **SwiftUI** — Apple's modern way to build iOS apps using Swift code. It comes with mock data so you can run it immediately without needing the backend server.

---

## Before you start — what you need

You need a **Mac** and **Xcode** installed (Xcode only runs on Mac).

### Install Xcode
1. Open the **App Store** on your Mac
2. Search for **"Xcode"**
3. Click **Get** / **Install** (it's free, but large — about 12 GB, so it may take a while)
4. Once installed, open Xcode at least once to let it finish setting up

**Minimum versions:**
- macOS 14 (Sonoma) or later
- Xcode 15 or later
- The app targets iOS 17+ — runs on iPhone 15 Pro simulator or a real iPhone with iOS 17+

---

## How to run the app

1. Open **Xcode**
2. Choose **File → Open…** from the menu bar
3. Navigate to `BookEase/Project/mobile/iOS/` and select **`BookEase.xcodeproj`**
4. Click **Open**
5. At the top of Xcode, click the device picker (next to the ▶ play button) and choose **"iPhone 15 Pro"** from the list
6. Press **⌘ R** (or click the ▶ play button) to build and run
7. The iPhone simulator will open and show the BookEase app!

---

## Screens in the app

Once running, you can navigate between these screens:

| Screen | How to get there |
|--------|-----------------|
| **Home** | Opens automatically — shows upcoming session, popular services, categories |
| **Services** | Tap "Explore" in the bottom tab bar |
| **Service detail** | Tap any service from the list |
| **Booking** | Tap "Book Now" on a service detail page |
| **Confirmation** | Shown after completing a booking |
| **My bookings** | Tap "Bookings" in the bottom tab bar |
| **Profile** | Tap "Profile" in the bottom tab bar |

---

## Folder layout

```
BookEase/
├── App/
│   ├── BookEaseApp.swift     ← The starting point of the app
│   └── RootTabView.swift     ← The bottom tab bar (Home, Explore, Bookings, Profile)
├── DesignSystem/
│   ├── Theme.swift           ← All colours, fonts, spacing (matches the website design)
│   └── Components/           ← Reusable UI pieces used across screens
├── Models/
│   └── MockData.swift        ← The fake data the app shows while backend isn't connected
└── Features/
    ├── Home/                 ← Home screen
    ├── Services/             ← Services list + detail
    ├── Booking/              ← Booking form + confirmation
    ├── Bookings/             ← Booking history
    └── Profile/              ← Profile and settings
```

---

## Adding custom fonts (optional but recommended)

The design uses **Space Grotesk** and **Plus Jakarta Sans** fonts. Without them, the app falls back to the system font (still looks good, just different).

To add them:
1. Download both fonts from https://fonts.google.com (search each name, click "Download family")
2. Unzip the downloaded files
3. In Xcode, right-click the `Resources` folder in the project navigator → **Add Files to "BookEase"**
4. Select all the `.ttf` files → make sure "Add to target: BookEase" is checked → click **Add**
5. Open `Info.plist` and add a key called **"Fonts provided by application"** with each font filename as a value

---

## Connecting to the real backend

Right now the app uses fake data from `MockData.swift`. When you're ready to connect to real data:

1. Start the backend server (see `../backend/README.md`)
2. In each `ViewModel` file, replace lines like:
   ```swift
   var services = MockData.services
   ```
   with a `URLSession` network call to `http://localhost:3000/api/services`

---

## Common problems

**"Xcode can't find the simulator"**
→ Open Xcode → Settings → Platforms → download the iOS 17 simulator.

**Build fails with "No account for team"**
→ You need to sign in with your Apple ID. Go to Xcode → Settings → Accounts → add your Apple ID. Then in the project settings, set the team to your personal account.

**The app crashes immediately**
→ Check the Xcode console at the bottom — there will be a red error message explaining what went wrong.
