package com.hdev.kermaxdevutils.utils

import android.app.Activity
import android.content.Context
import android.util.Log.d
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

class ReviewHelper {
    val TAG = ReviewHelper::class.java.simpleName

    private var reviewInfo: ReviewInfo? = null
    private lateinit var reviewManager: ReviewManager
    fun create(context: Context): ReviewInfo? {
        //Create the ReviewManager instance
        reviewManager = ReviewManagerFactory.create(context)
        //Request a ReviewInfo object ahead of time (pre-cache)
        val requestFlow = reviewManager.requestReviewFlow()
        requestFlow.addOnCompleteListener { request ->
            reviewInfo = if (request.isSuccessful) {
                //Received ReviewInfo object
                request.result
            } else {
                //Problem in receiving object
                null
            }
        }
        return reviewInfo
    }

    /**
     * Call this method when you want to show the dialog
     * @param activity Activity
     */
    fun askForReview(activity: Activity) {
        if (reviewInfo != null) {
            reviewManager.launchReviewFlow(activity, reviewInfo!!).addOnFailureListener {
                //Log error and continue with the flow
                d(TAG, it.message!!)
            }.addOnCompleteListener {
                //Log success and continue with the flow
                d(TAG, it.toString())
            }
        }
    }

    companion object {
        private var reviewHelper: ReviewHelper? = null

        @get:Synchronized
        val instance: ReviewHelper?
            get() {
                if (reviewHelper == null) {
                    reviewHelper = ReviewHelper()
                }
                return reviewHelper
            }
    }
}