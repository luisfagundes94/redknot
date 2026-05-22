# AGENTS.md

This file provides guidance to Google Gemini Agent when working with code in this repository.

## Build & Development Commands

Use Android CLI Skills

## Architecture Overview

**Pattern**: Clean Architecture + MVI (Model-View-Intent) 

**Modules:**
- `:app` — Entry point, navigation host, bottom bar
- `:core:common` — Base ViewModels, Hilt dispatcher qualifiers, test utilities
- `:core:testing` - MainDispatcherRule used in tests
- `:core:designsystem` — Compose components, theme, spacing tokens
- `:feature:trip` — All trip-related functionality (data/domain/presentation layers)

- Use Design Patterns when applicable
- Follow SOLID principles
- Follow the KISS principle (Keep It Simple, Stupid)
- Avoid repeating too much code

### Feature Module Structure (`:feature:trip`)

The module contains sub-features plus shared infrastructure:

- `com.luisfagundes.trip.*` — Trip (feature/trip/impl/src/main/java/com/luisfagundes/trip)
- `com.luisfagundes.itinerary.*` — Itinerary (feature/trip/impl/src/main/java/com/luisfagundes/itinerary)
- `com.luisfagundes.budget.*` — Budget (feature/trip/impl/src/main/java/com/luisfagundes/budget)
- `com.luisfagundes.documents.*` — Documents (feature/trip/impl/src/main/java/com/luisfagundes/documents)
- `com.luisfagundes.common.*` — Shared functionality (feature/trip/impl/src/main/java/com/luisfagundes/common)

Each sub-feature has its own `data/`, `domain/`, `presentation/`, and `di/` sub-packages.

**Visibility convention**: All domain models, use cases, repositories, ViewModels, and DI modules are `internal`. Only navigation routes and the `tripSection()` extension are public. Default to `internal` for new types.

### ViewModel Hierarchy

Two base classes in `core/presentation/arch/viewmodel/`:

- `ViewModel<State : UiState, Event : UiEvent, Effect : UiEffect>` — use when the screen needs MVI events and one-shot side effects like navigation (effects sent via `Channel`, collected as `uiEffect`).
- `StateViewModel<State : UiState, Event : UiEvent>` — use when MVI events are needed but no side effects are required; exposes only `uiState` and handles incoming `UiEvent`s.

Data flow: 
1. **User Action / Events**: The Compose screen dispatches immutable events implementing `UiEvent` by calling `viewModel.dispatchEvent(event)`. All public business methods on the ViewModel are kept `private`, exposing only `dispatchEvent`.
2. **State Updates**: The ViewModel handles incoming events within `dispatchEvent(event)`. It updates the immutable `uiState` via `setState { }` or `setStateOf<T> { }` (to only update state when matching a specific subtype in a sealed hierarchy).
3. **Side Effects**: One-shot side-effects (e.g. navigation, toasts) are dispatched from the ViewModel using `sendEffect { }` and collected in Compose screens using `CollectUiEffects(viewModel.uiEffect)`.

**State updates**: Use `setState { }` for unconditional updates. Use `setStateOf<SpecificState> { }` to only update when the current state matches a specific subtype in a sealed hierarchy (e.g., only mutate if currently in `Success` state).

### Navigation

Uses **androidx.navigation3** (experimental Nav3 library, not the stable `androidx.navigation`/NavController).

- Routes are `@Serializable data object` or `@Serializable data class` implementing `NavKey`
- New feature routes are registered by adding `entry<RouteType> { }` blocks inside the feature's `EntryProviderScope<NavKey>.featureSection()` extension, then wiring the section call in `AppNavDisplay.kt`
- `TopLevelDestinations` enum maps top-level routes to icons/labels for the bottom bar
- Key files in `:app`: `Navigator.kt`, `AppNavDisplay.kt`, `RedknotAppNavigationState.kt`

### Dependency Injection

Hilt throughout. Key qualifier annotations in `:core:common`:
- `@IoDispatcher`, `@DefaultDispatcher`, `@MainDispatcher`

All Hilt modules use `@InstallIn(SingletonComponent::class)`.

You can find the Hilt custom annotations at core/common/src/main/java/com/luisfagundes/core/common/di

### Data Layer (`:feature:trip`)

- **Room** with TypeConverters for `LocalDate`, `LocalTime`, `Duration`, `TripStatus`
- Itinerary items (Flight, Accommodation, Restaurant, Activity) use a polymorphic DAO factory pattern (`ItineraryDaoFactory`)
- **Retrofit** for Unsplash image API; auth injected via OkHttp interceptor
- Unsplash API key: `secrets-gradle-plugin` reads `UNSPLASH_ACCESS_KEY` from `secrets.properties` (not committed) and exposes it via `BuildConfig.UNSPLASH_ACCESS_KEY`
- Repositories return `Result<T>`; ViewModels fold on success/failure

### Testing

JUnit 5 + MockK + Turbine (Flow assertions)

- Use `MainDispatcherRule` from `:core:testing` for coroutine tests.
- Use Given, When and Then comments

```kotlin
@RegisterExtension
val dispatcher = MainDispatcherRule(UnconfinedTestDispatcher())

class MyViewModelTest {
    // turbine: viewModel.uiState.test { ... }
}
```

**Note**: Any new module with JUnit 5 tests must add `tasks.withType<Test> { useJUnitPlatform() }` in its `build.gradle.kts` and add `testImplementation` for `MainDispatcherRule`.

### Design System Conventions

- Use `@RedknotPreview` (combines dark + light previews) instead of bare `@Preview` on screen-level composables
- Use `MaterialTheme.spacing.*` (`verySmall=4dp`, `small=8dp`, `default=16dp`, `large=32dp`, `veryLarge=42dp`, `extraLarge=52dp`) instead of hardcoded `dp` values
- Use Material3 always

### New Module Checklist

- Enable core library desugaring (`isCoreLibraryDesugaringEnabled = true` + `coreLibraryDesugaring(libs.desugarJdkLibs)`) if the module uses `java.time.*` APIs
- Add `tasks.withType<Test> { useJUnitPlatform() }` if using JUnit 5
- Default types to `internal` visibility in feature modules

### Detekt Rules to Know

- `ForbiddenComment`: `TODO:`, `FIXME:`, `STOPSHIP:` will fail the build
- `ReturnCount` max: 2 per function
- `TooManyFunctions` threshold: 21 per class/file/interface/object
- `LongMethod` threshold: 60 lines (but `@Composable` functions are exempt)
- `WildcardImport` is active (no `import foo.*` except `java.util.*`)
- Compose rules from `io.nlopez.compose.rules` are active (ModifierMissing, ViewModelForwarding, ViewModelInjection, MutableStateParam, etc.)
