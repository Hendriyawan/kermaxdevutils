package com.hdev.kermaxdevutils.utils.update

import android.content.IntentSender
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.hdev.kermaxdevutils.utils.Constants

class KDAppUpdateManager : LifecycleObserver {

    /**
     * callback method where update event are reported
     */
    interface AppUpdateCallback {
        /**On Update Error
         * @param code the code
         * @param error the error
         */
        fun onUpdateError(code: Int, error: Throwable)

        /**
         * Monitoring update state of flexible download
         * @param status status
         */
        fun onAppUpdateStatus(status: AppUpdateStatus)
    }

    //declaration variable
    private val LOG_TAG = KDAppUpdateManager::class.java.simpleName
    private var activity: AppCompatActivity? = null
    private lateinit var appUpdateManager: AppUpdateManager
    private var requestCode = 620598
    private var snackBarMessage = "An update has just been downloaded."
    private var snackBarAction = "Restart"
    private var mode: Constants.UpdateMode = Constants.UpdateMode.FLEXIBLE
    private var resumeUpdates = true
    private var useCustomNotification = false
    private var callback: AppUpdateCallback? = null
    private lateinit var snackBar: Snackbar
    private val appUpdateStatus: AppUpdateStatus = AppUpdateStatus()

    //listener
    /*private val installStateUpdateListener = InstallStateUpdatedListener { installState ->
        appUpdateStatus.setInstallState(installState)
        reportStatus()

        //show module progress, log state or install the update
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            //after update is downloaded, show notification
            //and request user confirmation to restart the app
            popupSnackBarForUserConfirmation()
        }
    }*/

    private val installStateUpdateListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener {
            appUpdateStatus.setInstallState(it)
            reportStatus()

            //show module progress, log state or install the update
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                //after update is downloaded, show notification
                //and request user confirmation to restart the app
                popupSnackBarForUserConfirmation()
            }

            if (it.installStatus() == InstallStatus.FAILED) {
                Log.d(LOG_TAG, "Install Filed : ${it.installErrorCode()}")
            }
        }

    private constructor(activity: AppCompatActivity) {
        this.activity = activity
        setupSnackBar()
        init()
    }

    private constructor(activity: AppCompatActivity, requestCode: Int) {
        this.activity = activity
        this.requestCode = requestCode
        init()
    }

    companion object {
        private var instance: KDAppUpdateManager? = null

        /**
         * make a Builder
         * @param activity current Activity
         * @return a new {@link KDAppUpdateManager} instance
         */

        fun Builder(activity: AppCompatActivity): KDAppUpdateManager? {
            if (instance == null) {
                instance = KDAppUpdateManager(activity)
            }
            return instance
        }

        fun Builder(activity: AppCompatActivity, requestCode: Int): KDAppUpdateManager? {
            if (instance == null) {
                instance = KDAppUpdateManager(activity, requestCode)
            }
            return instance
        }
    }

    private fun init() {
        setupSnackBar()
        appUpdateManager = AppUpdateManagerFactory.create(activity!!)
        activity?.lifecycle!!.addObserver(this)

        if (mode == Constants.UpdateMode.FLEXIBLE) appUpdateManager.registerListener(
            installStateUpdateListener
        )
        checkForUpdate(false)
    }

    //setup snack bar
    private fun setupSnackBar() {
        val rootView = activity?.window!!.decorView.findViewById<View>(android.R.id.content)
        snackBar = Snackbar.make(
            rootView,
            snackBarMessage,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(snackBarAction) {
                //Triggers the completion of the update of the app for flexible flow
                appUpdateManager.completeUpdate()

            }
        }
    }

    fun mode(mode: Constants.UpdateMode): KDAppUpdateManager {
        this.mode = mode
        return this
    }

    fun resumeUpdates(resumeUpdates: Boolean): KDAppUpdateManager {
        this.resumeUpdates = resumeUpdates
        return this
    }

    fun callback(callback: AppUpdateCallback): KDAppUpdateManager {
        this.callback = callback
        return this
    }

    fun useCustomNotification(useCustomNotification: Boolean): KDAppUpdateManager {
        this.useCustomNotification = useCustomNotification
        return this
    }

    fun snackMessage(snackBarMessage: String): KDAppUpdateManager {
        this.snackBarMessage = snackBarMessage
        setupSnackBar()
        return this
    }

    fun snackBarAction(snackBarAction: String): KDAppUpdateManager {
        this.snackBarAction = snackBarAction
        setupSnackBar()
        return this

    }

    fun snackBarActionColor(color: Int): KDAppUpdateManager {
        snackBar.setActionTextColor(color)
        return this
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (resumeUpdates) {
            checkNewAppVersionState()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        unregisterListener()
    }

    /**
     * check for update availability if there will be update available
     * will start the update process with the selected
     */
    fun checkAppUpdate() {
        checkForUpdate(true)
    }

    /**
     * Triggers completion app update for flexible flow
     */
    fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }

    /**
     * Check for update availability, if there will be an update available
     * will start update
     */
    private fun checkForUpdate(startUpdate: Boolean) {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            appUpdateStatus.setAppUpdateInfo(appUpdateInfo)
            if (startUpdate) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    //request update
                    if (mode == Constants.UpdateMode.FLEXIBLE && appUpdateInfo.isUpdateTypeAllowed(
                            AppUpdateType.FLEXIBLE
                        )
                    ) {
                        //start an update
                        startAppUpdateFlexible(appUpdateInfo)
                    } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        //start update
                        startAppUpdateImmediate(appUpdateInfo)
                    }
                    Log.d(
                        LOG_TAG,
                        "checkForUpdate: Update available, version code ${appUpdateInfo.availableVersionCode()} "
                    )
                } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE) {
                    Log.d(
                        LOG_TAG,
                        "checkForUpdate: No update available, code ${appUpdateInfo.updateAvailability()}"
                    )
                }
            }
            reportStatus()
        }
    }

    private fun startAppUpdateImmediate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                //current activity making request update app
                activity!!,
                //include request code to later monitor this update
                requestCode
            )

        } catch (e: IntentSender.SendIntentException) {
            Log.e(LOG_TAG, "error in startAppUpdateImmediate", e)
            reportUpdateError(Constants.UPDATE_ERROR_START_APP_UPDATE_IMMEDIATE, e)
        }
    }

    /**
     * starting update app flexible
     * @param appUpdateInfo AppUpdateInfo
     */
    private fun startAppUpdateFlexible(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.FLEXIBLE,
                //the current activity making update request
                activity!!,
                //include request code to later monitor this update request.
                requestCode
            )
        } catch (e: IntentSender.SendIntentException) {
            Log.e(LOG_TAG, "error in startAppUpdateFlexible", e)
            reportUpdateError(Constants.UPDATE_ERROR_START_APP_UPDATE_FLEXIBLE, e)
        }
    }

    /**
     * display the snack bar notification and call to action
     * Needed only for Flexible app update
     */
    private fun popupSnackBarForUserConfirmation() {
        if (!useCustomNotification) {
            if (snackBar != null && snackBar.isShownOrQueued) {
                snackBar.dismiss()
            }
            snackBar.show()
        }
    }

    /**
     * Check that the update is not stalled during 'onResume()'
     * However, you should execute this check at all app entry points
     */
    private fun checkNewAppVersionState() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            appUpdateStatus.setAppUpdateInfo(appUpdateInfo)
            //FLEXIBLE
            //if the update is downloaded but not installed
            //notify the user to complete update
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForUserConfirmation()
                reportStatus()
                Log.d(
                    LOG_TAG,
                    "checkNewAppVersionState: resuming flexible update. Code ${appUpdateInfo.updateAvailability()}"
                )
            }

            //IMMEDIATE
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                startAppUpdateImmediate(appUpdateInfo)
                Log.d(
                    LOG_TAG,
                    "checkNewAppVersionState: resuming immediate update. Code ${appUpdateInfo.updateAvailability()}"
                )
            }
        }

    }

    private fun unregisterListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdateListener)
        }
    }

    private fun reportUpdateError(errorCode: Int, error: Throwable) {
        if (callback != null) {
            callback?.onUpdateError(errorCode, error)
        }
    }

    private fun reportStatus() {
        if (callback != null) {
            callback?.onAppUpdateStatus(appUpdateStatus)
        }
    }
}