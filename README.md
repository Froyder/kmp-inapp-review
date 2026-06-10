# KMP In-App Review

A Kotlin Multiplatform library that wraps the native in-app review APIs
for Android and iOS under a unified, coroutine-friendly interface.

- **Android**: Google Play In-App Review API
- **iOS**: SKStoreReviewController
- **Unified API**: single `requestReview()` call from shared code

## Installation

```kotlin
// build.gradle.kts
commonMain.dependencies {
    implementation("io.github.froyder:kmp-inapp-review:1.0.0")
}
```

## Usage

```kotlin
// commonMain — works for both platforms
val reviewManager = ReviewManager() // iOS
val reviewManager = ReviewManager(activity) // Android

viewModelScope.launch {
    try {
        reviewManager.requestReview()
    } catch (e: Exception) {
        // handle error
    }
}
```

## Important notes

- Neither platform guarantees the dialog will be shown
- Android rate-limits via Google Play; iOS rate-limits to 3 times per year
- The library contract is "flow completed without error", not "dialog was shown"

## Platform support

| Platform | Minimum version |
|---|---|
| Android | API 21 |
| iOS | iOS 14 |

## License

Apache 2.0