package com.hdev.kermaxdevutils.utils.update

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

/**
 * class Wrapper for AppUpdateManager
 * used by AppUpdateManager
 */
class AppUpdateStatus {
    private val NO_UPDATE = 0
    private var appUpdateInfo: AppUpdateInfo? = null
    private var installState: InstallState? = null

    fun setAppUpdateInfo(appUpdateInfo: AppUpdateInfo) {
        this.appUpdateInfo = appUpdateInfo
    }

    fun setInstallState(installState: InstallState) {
        this.installState = installState
    }

    fun isDownloading(): Boolean {
        if (installState != null) return installState?.installStatus() == InstallStatus.DOWNLOADING
        return false
    }

    fun isDownloaded(): Boolean {
        if (installState != null) return installState?.installStatus() == InstallStatus.DOWNLOADED
        return false
    }

    fun isFailed(): Boolean {
        if (installState != null) return installState?.installStatus() == InstallStatus.FAILED
        return false
    }

    fun isUpdateAvailable(): Boolean {
        if (appUpdateInfo != null) return appUpdateInfo?.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
        return false
    }

    fun availableVersionCode(): Int {
        if (appUpdateInfo != null) return appUpdateInfo?.availableVersionCode()!!
        return NO_UPDATE
    }
}