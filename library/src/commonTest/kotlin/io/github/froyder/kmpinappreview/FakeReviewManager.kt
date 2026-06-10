package io.github.froyder.kmpinappreview

class FakeReviewManager {
    var requestReviewCalled = false
    var shouldThrow = false

    suspend fun requestReview() {
        if (shouldThrow) throw Exception("Review flow failed")
        requestReviewCalled = true
    }
}