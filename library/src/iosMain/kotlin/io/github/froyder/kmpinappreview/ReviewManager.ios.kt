package io.github.froyder.kmpinappreview

import platform.StoreKit.SKStoreReviewController

public actual class ReviewManager {
    public actual suspend fun requestReview() {
        SKStoreReviewController.requestReview()
    }
}