package com.hdev.kermaxdevutils.utils.update

import androidx.appcompat.app.AppCompatActivity

class InAppUpdateManager {

    private var activity: AppCompatActivity
    private var requestCode = 620498

    private constructor(activity: AppCompatActivity) {
        this.activity = activity
    }

    private constructor(activity: AppCompatActivity, requestCode: Int) {
        this.activity = activity
        this.requestCode = requestCode
    }

    companion object {
        private var instance: InAppUpdateManager? = null
        fun Builder(activity: AppCompatActivity): InAppUpdateManager? {
            if (instance == null) {
                instance = InAppUpdateManager(activity)
            }
            return instance
        }

        fun Builder(activity: AppCompatActivity, requestCode: Int): InAppUpdateManager? {
            if (instance == null) {
                instance = InAppUpdateManager(activity, requestCode)
            }
            return instance
        }
    }
}