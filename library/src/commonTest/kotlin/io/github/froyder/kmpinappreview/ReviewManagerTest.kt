package io.github.froyder.kmpinappreview

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ReviewManagerTest {

    @Test
    fun `requestReview completes without error`() = runTest {
        val fake = FakeReviewManager()
        fake.requestReview()
        assertTrue(fake.requestReviewCalled)
    }

    @Test
    fun `requestReview propagates exception`() = runTest {
        val fake = FakeReviewManager()
        fake.shouldThrow = true
        assertFailsWith<Exception> {
            fake.requestReview()
        }
    }
}