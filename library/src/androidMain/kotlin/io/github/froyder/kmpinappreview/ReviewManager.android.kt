package io.github.froyder.kmpinappreview

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

public actual class ReviewManager(private val activity: Activity) {

    public actual suspend fun requestReview() {
        val manager = ReviewManagerFactory.create(activity)

        val reviewInfo = suspendCancellableCoroutine { continuation ->
            manager.requestReviewFlow()
                .addOnSuccessListener { reviewInfo ->
                    continuation.resume(reviewInfo)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        suspendCancellableCoroutine { continuation ->
            manager.launchReviewFlow(activity, reviewInfo)
                .addOnCompleteListener {
                    // Always completes — success or silently skipped by OS
                    continuation.resume(Unit)
                }
        }
    }
}