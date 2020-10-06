package com.hdev.kermaxdevutils.utils

import android.app.Activity
import android.content.IntentSender
import android.util.Log
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class UpdateHelper {
    val TAG = UpdateHelper::class.java.simpleName

    companion object {
        const val RC_APP_UPDATE = 98
        private var updateHelper: UpdateHelper? = null

        @get:Synchronized
        val instance: UpdateHelper?
            get() {
                if (updateHelper == null) {
                    updateHelper = UpdateHelper()
                }
                return updateHelper
            }
    }

    fun checkUpdate(activity: Activity): AppUpdateManager {
        val appUpdateManager = AppUpdateManagerFactory.create(activity)
        //return an Intent object that you use to check for an update
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        //check that platform will allow the specified type for update
        Log.d(TAG, "Checking for updates")
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE)) {
                //request update
                Log.d(TAG, "Update available")
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        activity,
                        RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            } else {
                Log.d(TAG, "No Update available")
            }
        }
        return appUpdateManager
    }

    fun triggeredUpdateProgress(appUpdateManager: AppUpdateManager, activity: Activity) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        activity, RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }
}