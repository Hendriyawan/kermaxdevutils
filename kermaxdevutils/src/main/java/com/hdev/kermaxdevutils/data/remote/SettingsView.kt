package com.hdev.kermaxdevutils.data.remote

import com.hdev.kermaxdevutils.data.model.Settings

class SettingsView {
    interface Presenter {
        fun getSettings(url : String)
    }

    interface MainView {
        fun onStartProgress()
        fun onStopProgress()
        fun onSettingsLoaded(response : Settings)
        fun onFailed(message : String)
        fun onOffline()
    }
}