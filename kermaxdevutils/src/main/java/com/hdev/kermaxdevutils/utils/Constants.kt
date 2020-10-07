package com.hdev.kermaxdevutils.utils

class Constants {

    companion object {
        const val BANNER_ID_DEBUG = "ca-app-pub-3940256099942544/6300978111"
        const val INTERSTITIAL_AD_DEBUG = "ca-app-pub-3940256099942544/1033173712"
        const val UPDATE_ERROR_START_APP_UPDATE_FLEXIBLE = 100
        const val UPDATE_ERROR_START_APP_UPDATE_IMMEDIATE = 102
    }

    enum class UpdateMode {
        FLEXIBLE,
        IMMEDIATE
    }
}