# KMP In-App Review

![Maven Central](https://img.shields.io/maven-central/v/io.github.froyder/kmp-inapp-review)
![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)

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

Create a platform-specific instance once, at app startup:

```kotlin
// Android
val reviewManager = ReviewManager(activity)

// iOS
val reviewManager = ReviewManager()
```

Then call from shared `commonMain` code — identical for both platforms:

```kotlin
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
| iOS | iOS 16 |

## License

Apache 2.0