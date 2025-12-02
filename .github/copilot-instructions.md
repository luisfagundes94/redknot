# GitHub Copilot Instructions for Modern Android Development

## Language & Syntax
- Use Kotlin as the primary language for all Android development
- Apply idiomatic Kotlin patterns: data classes, sealed classes, extension functions, and scope functions
- Leverage Kotlin coroutines for asynchronous operations
- Use nullable types appropriately with safe calls and elvis operators
- Prefer immutability with `val` over `var` when possible

## Architecture
- Follow Clean Architecture principles with clear separation of concerns
- Implement MVVM (Model-View-ViewModel) architecture
- Structure projects with feature-based modules when complexity warrants
- Use dependency injection with Hilt
- Keep ViewModels framework-agnostic and testable

## UI Development
- Use Jetpack Compose for all new UI components
- Follow Material Design 3 guidelines and use Material Components
- Implement responsive layouts that adapt to different screen sizes
- Use `remember`, `rememberSaveable`, and proper state hoisting
- Create reusable composables with clear responsibilities
- Handle configuration changes gracefully

## Data Layer
- Use Room for local database operations
- Implement Repository pattern for data source abstraction
- Implement DataSource interfaces for remote and local data handling
- Use Retrofit with Kotlin coroutines for network calls
- Use Kotlin's Result type for handling success and error cases
- Use DataStore for preferences instead of SharedPreferences


## Reactive Programming
- Use `suspend` functions for single-shot network calls and one-time operations
- Reserve Flow for actual streams of data (e.g., database observations, real-time updates, continuous data sources)
- Apply StateFlow and SharedFlow when managing UI state or event streams
- Transform data streams using Flow operators only when dealing with reactive data sources
- Handle lifecycle awareness with `collectAsStateWithLifecycle()` in Compose for Flow-based state

## Testing
- Write unit tests for ViewModels, use cases, and repositories
- Use JUnit 5 where possible, fallback to JUnit 4 if needed
- Mock dependencies with MockK for Kotlin
- Write integration tests for database operations with Room
- Follow the Given-When-Then pattern

## Code Quality
- Follow official Kotlin coding conventions
- Keep functions small and focused on single responsibilities
- Use meaningful variable and function names
- Add KDoc comments for public APIs
- Avoid deep nesting with early returns and guard clauses
- Apply SOLID principles consistently
- Apply design patterns where appropriate

## Dependencies & Gradle
- Use Version Catalogs (libs.versions.toml) for dependency management
- Keep dependencies up to date with latest stable versions
- Use Gradle Kotlin DSL (build.gradle.kts)
- Enable and configure R8/ProGuard for release builds
- Implement proper build variants and product flavors when needed

## Performance
- Avoid memory leaks by properly managing lifecycles
- Use lazy initialization where appropriate
- Optimize RecyclerView/LazyColumn with proper keys and diff callbacks
- Profile and optimize database queries
- Use WorkManager for background tasks
- Implement proper image loading with Coil or Glide

## Security
- Never hardcode API keys or sensitive credentials
- Use BuildConfig or environment variables for secrets
- Implement proper certificate pinning for network security
- Validate user inputs and sanitize data
- Use encrypted DataStore or EncryptedSharedPreferences for sensitive data

## Navigation
- Use Jetpack Navigation 3 Compose for navigation
- Define navigation graphs clearly with type-safe arguments
- Handle deep links appropriately
- Implement proper back stack management

## Error Handling
- Provide meaningful error messages to users
- Log errors appropriately for debugging
- Implement retry mechanisms for network failures
- Handle edge cases explicitly

## Accessibility
- Add content descriptions to all interactive elements
- Support TalkBack and other accessibility services
- Ensure proper touch target sizes (minimum 48dp)
- Provide sufficient color contrast
- Test with accessibility scanner

## Version Control
- Write clear, descriptive but not long commit messages
- Keep commits atomic and focused
- Create feature branches for new development
- Include relevant context in pull request descriptions