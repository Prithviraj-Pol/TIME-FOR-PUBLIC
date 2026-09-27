# TIME FOR PUBLIC

> **Transforming Citizen-Government Interaction with Real-Time Transparency & Intelligence**

**TIME FOR PUBLIC** is an integrated public administration assistance platform designed to eliminate unnecessary visits to government offices, reduce bureaucratic confusion, and empower citizens with accurate, real-time guidance.

---

## 🚀 Key Features

- **🏛️ Citizen Service Guidance**: Clear roadmaps for public services (Revenue, Transport, Municipal, Welfare, etc.) with exact issuing authorities.
- **📄 Smart Document Checklists**: Dynamic checklists with required documents, valid formats, and eligibility criteria before visiting offices.
- **📍 Real-Time Office & Officer Availability**: Geofenced availability status so citizens know if officers are present in office, in meetings, or on field duty.
- **💡 Government Schemes Hub**: Discover central and state government welfare schemes with direct links to official application portals.
- **🤖 AI Public Assistant**: Natural language civic query answering, scheme recommendations, and eligibility checks.
- **🛡️ Dual-Role Experience**: Role-based interface for **Citizens** (guidance & tracking) and **OFFICER** (status toggling & queue management).

---

## 📂 Repository Structure

```text
TIME-FOR-PUBLIC/
├── android/            # Native Android App (Kotlin + Jetpack Compose + Material 3)
│   ├── app/
│   │   └── src/main/java/com/timeforpublic/
│   │       ├── core/           # Common utilities, network client, geofence, security
│   │       ├── data/           # Repository implementations, remote DTOs, local cache
│   │       ├── di/             # Dependency injection modules / service locator
│   │       ├── domain/         # Domain entities, models, and repository contracts
│   │       ├── feature/        # Feature screens (Home, Auth, Schemes, Documents, AI, Offices, Officer)
│   │       ├── navigation/     # Jetpack Compose Navigation & Type-safe routes
│   │       └── ui/             # Design system, themes (colors, typography, shapes), shared components
├── backend/            # FastAPI / Node.js Backend services
├── ai-engine/          # RAG pipeline, vector embeddings, civic LLM assistants
└── docs/               # Architecture, API specifications, and database schema documentation
```

---

## 🛠️ Android Development Quick Start

### Prerequisites
- Android Studio Ladybug / Meerkat (or newer)
- JDK 17+ (or JDK 21 / 25)
- Android SDK Platform 34 or 35

### Build Commands
```powershell
cd android

# Clean build cache
.\gradlew clean

# Build Debug APK
.\gradlew assembleDebug

# Run Unit Tests
.\gradlew test

# Install to connected device
.\gradlew installDebug
```

---

## 🗺️ Roadmap & Milestones

- [x] **Phase 1: Foundation & Navigation** (Material 3 Theme, Splash, Role Auth, Home Dashboard)
- [ ] **Phase 2: Government Services & Search** (Department catalog, scheme details, office directory)
- [ ] **Phase 3: Document Checklists & Eligibility Engine** (Dynamic criteria validation)
- [ ] **Phase 4: Real-time Officer Availability** (Firebase Realtime DB & notifications)
- [ ] **Phase 5: AI Civic Assistant** (FastAPI backend + RAG pipeline)
- [ ] **Phase 6: Geofenced Presence & Security** (Automated check-ins for officers)

---

## 📄 License
This project is developed under open-source civic technology standards for public welfare and digital governance.
