package io.github.froyder.kmpinappreview

public actual class ReviewManager {
    public actual suspend fun requestReview() {
        // No-op on JVM/Desktop — in-app review not supported
    }
}