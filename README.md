# Redknot

A trip planning Android app built with Kotlin and Jetpack Compose.

## Features

- Create, view, edit, and delete trips
- Manage itinerary items per trip: flights, accommodations, restaurants, and activities
- Track budget and expenses per trip (adding and categorizing expenses)
- Document management per trip (storing travel documents, tickets, and bookings)

## Screenshots
<p float="left">
  <img src="screenshots/trip_form_empty.png" width="200" /> 
  <img src="screenshots/trip_form.png" width="200" /> 
  <img src="screenshots/trip_list.png" width="200" /> 
  <img src="screenshots/trip_itinerary.png" width="200" />
</p>

## Tech Stack

| Layer | Library |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVVM (MVI-style) |
| Navigation | androidx.navigation3 |
| DI | Hilt |
| Database | Room |
| Networking | Retrofit + OkHttp |
| Image loading | Coil |
| Testing | JUnit 5 + MockK + Turbine |
| Static analysis | Detekt |

**Min SDK:** 24 | **Target SDK:** 36

## Module Structure

```
redknot/
├── :app                  # Entry point, navigation host, bottom bar
├── :core
│   ├── :common           # Base ViewModels, Hilt dispatcher qualifiers
│   ├── :designsystem     # Compose components, Material 3 theme, spacing tokens
│   └── :testing          # Shared test utilities (MainDispatcherRule)
└── :feature:trip
    ├── :api              # Public navigation routes & interfaces
    └── :impl             # Data, domain, and presentation layers (Trip, Itinerary, Budget, Documents)
```

## Setup

1. Add your Unsplash API key to `local.properties` at the project root:
   ```
   UNSPLASH_ACCESS_KEY=your_key_here
   ```
2. Build and run via Android Studio or:
   ```bash
   ./gradlew assembleDebug
   ```
