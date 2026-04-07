# CodeWars Android App

An Android application built with **Clean Architecture** and **MVVM** pattern that allows users to explore [Codewars](https://www.codewars.com) profiles and their kata challenges using live APIs.

---

## Demo

### Screenshots

| Search User | User Details | Challenge Details |
|:-----------:|:------------:|:-----------------:|
| ![Search User](screenshots/search_user.png) | ![User Details](screenshots/user_details.png) | ![Challenge Details](screenshots/challenge_details.png) |

### Video

https://github.com/user-attachments/assets/YOUR_VIDEO_ASSET_ID

---

## Features

- **Search User** — Search any Codewars username and view their profile
- **Completed Challenges** — Browse paginated list of completed kata
- **Authored Challenges** — Browse paginated list of authored kata
- **Challenge Details** — View full kata description, tags, and ranking info
- **Offline Support** — Room database caches data for offline access
- **Pagination** — Paging 3 with network + local data via RemoteMediator

---

## Architecture

The project follows **Clean Architecture** with three distinct layers:

```
presentation/   →   domain/   →   data/
```

| Layer | Responsibilities |
|-------|-----------------|
| `presentation` | Activities, Fragments, ViewModels, UI adapters |
| `domain` | Use cases, repository interfaces, domain models |
| `data` | Repository implementations, Room DB, Retrofit, mappers |

**Dependency Injection** is handled by **Dagger Hilt**, with modules for Network, Persistence, and Repositories.

---

## Tech Stack

| Category | Library |
|----------|---------|
| Architecture | MVVM + Clean Architecture |
| DI | Dagger Hilt 2.59 |
| Async | Kotlin Coroutines + Flow |
| Networking | Retrofit 3 + OkHttp 3 |
| JSON Parsing | Moshi 1.15 |
| Local DB | Room 2.8 |
| Pagination | Paging 3 |
| Navigation | AndroidX Navigation |
| UI | Material Design 3, ConstraintLayout, SwipeRefreshLayout |
| Search UI | PersistentSearchView |
| Responsive Sizes | SDP / SSP |
| Memory Leak Detection | LeakCanary (debug) |
| Testing | JUnit 4, MockK, Turbine, Espresso |

---

## Screens

### 1. Search User
Enter a Codewars username to look up their profile. Results are cached locally with Room.

### 2. User Details
Displays the user's **Completed** and **Authored** kata in two tabs with infinite scroll via Paging 3.

### 3. Challenge Details
Full kata view showing description, tags, total completions, and rank information.

---

## Getting Started

1. Clone the repository
   ```bash
   git clone https://github.com/YOUR_USERNAME/CodeWars.git
   ```
2. Open in **Android Studio Hedgehog** or newer
3. Build and run on a device or emulator (API 21+)

> No API key required — uses the public [Codewars API](https://dev.codewars.com/).

---

## Project Structure

```
com.faraz.codewars/
├── data/
│   ├── local/          # Room DAOs and Entities
│   ├── remote/         # Retrofit service and DTOs
│   ├── mapper/         # Data ↔ Domain mappers
│   ├── mediator/       # RemoteMediator (Paging 3)
│   └── repository/     # Repository implementations
├── domain/
│   ├── model/          # Domain models
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Business logic use cases
├── presentation/
│   └── ui/
│       ├── users/              # Search & user list screen
│       ├── userdetails/        # Tabbed user detail screen
│       └── challengedetails/   # Kata detail screen
└── di/                 # Hilt modules
```
