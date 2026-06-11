#  CarWare

CarWare is a **Kotlin Multiplatform (KMP)** mobile application designed to help users manage their vehicles, track maintenance activities, and connect with maintenance service providers.  
The project uses **Compose Multiplatform** to share UI and business logic across platforms while maintaining a clean and scalable architecture.

---

## 🎯 Project Objective

- Provide a centralized platform for vehicle and maintenance management
- Reduce maintenance tracking overhead for vehicle owners
- Demonstrate modern mobile development using Kotlin Multiplatform
- Apply clean architecture and best practices in a real-world project

---

## ✨ Core Features

- Vehicle management (add, update, view vehicle details)
- Maintenance tracking and history
- Authentication and secure user sessions
- Token-based API communication
- Shared navigation and UI logic across platforms
- Scalable and maintainable architecture

---

## 🏗 Architecture Overview

CarWare follows **MVVM** combined with the **Repository Pattern**, ensuring separation of concerns and testability.

```
Compose UI
   ↓
ViewModel
   ↓
Repository
   ↓
Remote Data Source (Ktor Client)
```

### Key Principles
- Single source of truth for data
- Platform-independent business logic
- Clear boundaries between UI, domain, and data layers

---

## 🧠 State Management

- ViewModels manage UI state
- Kotlin Coroutines handle asynchronous operations
- State is exposed to Compose using immutable state models

---

## 🌐 Networking & API Handling

- API communication is handled using **Ktor Client**
- JSON serialization via **kotlinx.serialization**
- Authentication tokens are:
  - Retrieved on login/signup
  - Stored securely
  - Automatically attached to authorized requests
- Centralized error handling for unauthorized responses

---

## 🧭 Navigation Logic

- Shared Compose navigation system
- Start destination is determined dynamically based on:
  - Onboarding completion
  - Authentication status
  - Vehicle availability

This ensures a smooth and context-aware user flow.

---

## 🛠 Tech Stack

### Languages & Frameworks
- Kotlin
- Kotlin Multiplatform (KMP)
- Compose Multiplatform

### Architecture & Patterns
- MVVM
- Repository Pattern
- Clean Architecture

### Networking & Data
- Ktor Client
- REST APIs
- Kotlinx Serialization

### Tools
- Android Studio
- Gradle
- Git & GitHub

---

## 📱 Platform Support

- Android ✅
- iOS 🚧 (shared logic and UI already prepared)

---

## 📂 Project Structure

```
shared/
 ├── ui/           # Compose screens and components
 ├── viewmodel/    # ViewModels and state holders
 ├── repository/   # Data access layer
 ├── network/      # Ktor client & API services
 ├── model/        # Data models

androidApp/
iosApp/
```

---

## 🚀 Getting Started

### Requirements
- Android Studio Hedgehog or newer
- JDK 17+
- Kotlin Multiplatform enabled

### Run on Android
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Run the `androidApp` module

---

## 🔮 Planned Enhancements

- Push notifications for maintenance reminders
- Offline data caching
- Service provider ratings and reviews
- iOS-specific UI optimizations

---

## 🤝 Contribution

This project is open for improvements and extensions.  
Contributions, issues, and feature requests are welcome
