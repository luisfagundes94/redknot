# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Redknot is an Android travel planning application built with modern Android development practices. The app follows Clean Architecture with MVVM pattern and uses Jetpack Compose for UI.

## Module Structure

The project is organized into feature-based modules:

- **app**: Main application module containing navigation setup and app entry point
- **core**: Shared infrastructure (dispatchers, DI qualifiers)
- **designsystem**: Reusable UI components, theme definitions, and design tokens
- **feature:trip**: Trip management feature module with complete Clean Architecture layers

## Build Commands

```bash
# Build the project
./gradlew build

# Run debug build on connected device
./gradlew installDebug

# Run tests
./gradlew test

# Run a specific test class
./gradlew test --tests "com.example.ClassName"

# Run instrumented tests
./gradlew connectedAndroidTest

# Clean build
./gradlew clean build

# Generate coverage report
./gradlew jacocoTestReport
```

## Architecture Patterns

### Clean Architecture Layers

Each feature module follows a strict three-layer architecture:

1. **Data Layer** (`data/`):
   - `datasource/`: Interfaces and implementations for data sources (Room, network)
   - `model/`: Data entities (e.g., `TripEntity` for Room)
   - `dao/`: Room Data Access Objects
   - `database/`: Room database definitions
   - `repository/`: Repository implementations (e.g., `TripRepositoryImpl`)
   - `mapper/`: Bidirectional mapping between data entities and domain models

2. **Domain Layer** (`domain/`):
   - `model/`: Business models (e.g., `Trip`, `TripSection`)
   - `repository/`: Repository interfaces
   - `usecase/`: Single-responsibility use cases (e.g., `CreateTripUseCase`, `GetTripListUseCase`, `ValidateTitleUseCase`)

3. **Presentation Layer** (`presentation/`):
   - `viewmodel/`: ViewModels with Hilt injection
   - `state/`: UI state data classes (e.g., `TripFormUiState`)
   - `effect/`: One-time UI effects using Channel (e.g., `TripFormUiEffect`)
   - `screen/`: Composable screens
   - `navigation/`: Feature navigation setup
   - `mapper/`: Mapping between domain and presentation models

### Key Architectural Decisions

- **Navigation**: Uses Jetpack Navigation 3 with custom `Navigator` class managing per-route back stacks
- **Dependency Injection**: Hilt with KSP for annotation processing
- **Dispatcher Management**: Core module provides `@IoDispatcher`, `@DefaultDispatcher`, `@MainDispatcher` qualifiers
- **Error Handling**: Uses Kotlin's `Result` type throughout the data and domain layers
- **Validation**: Dedicated use cases for form validation (e.g., `ValidateTitleUseCase`, `ValidateDateUseCase`)
- **State Management**:
  - `MutableStateFlow` for UI state
  - `Channel` with `receiveAsFlow()` for one-time effects
  - `collectAsStateWithLifecycle()` in Compose
- **Date Handling**: Uses `java.time.LocalDate` with core library desugaring enabled

### Data Flow Example

Trip creation follows this flow:
1. User input → ViewModel validates via `ValidateXxxUseCase`
2. ViewModel updates `TripFormUiState` with validation errors
3. On submit → ViewModel calls `CreateTripUseCase`
4. UseCase calls `TripRepository.createTrip()`
5. Repository uses `TripMapper` to convert domain model to `TripEntity`
6. DataSource persists via Room DAO
7. Result flows back through layers using Kotlin's `Result` type
8. ViewModel sends effect via Channel (e.g., `NavigateBack` or `ShowErrorToast`)

## Dependency Management

All dependencies are managed via Version Catalogs in `gradle/libs.versions.toml`. When adding dependencies:

1. Add version to `[versions]` section
2. Add library/plugin declaration to appropriate section
3. Reference using `libs.` notation in build files

Example:
```kotlin
implementation(libs.androidx.core.ktx)
```

## Important Conventions

### Module Visibility

Feature modules use `internal` visibility extensively to enforce module boundaries. Public APIs are explicitly exposed.

### Testing Strategy

- Unit tests: ViewModels, use cases, repositories using JUnit and MockK
- Integration tests: Room database operations
- UI tests: Composable screens with instrumented tests
- Follow Given-When-Then pattern

### Code Organization

- Keep use cases focused on single operations
- Each validation has its own use case
- Mappers handle all data transformation between layers
- Effects are one-time events (navigation, toasts), state is persistent UI data
- ViewModels use `viewModelScope` with injected dispatchers for testability

## Code Quality
- Follow official Kotlin coding conventions
- Keep functions small and focused on single responsibilities
- Use meaningful variable and function names
- Add KDoc comments for public APIs
- Apply SOLID principles consistently
- Apply design patterns where appropriate

### Jetpack Compose Patterns

- Material Design 3 components from `designsystem` module
- State hoisting with `remember` and `rememberSaveable`
- Lottie animations for loading states and empty views
- Coil for image loading with OkHttp network backend

## Module Dependencies

```
app → core, designsystem, feature:trip
feature:trip → core, designsystem
designsystem → (no internal dependencies)
core → (no internal dependencies)
```

When adding new features, follow this dependency hierarchy.
