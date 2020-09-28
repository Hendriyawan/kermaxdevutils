package com.hdev.kermaxdevutils.data.remote

import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.hdev.kermaxdevutils.data.model.SettingsResponse
import com.hdev.kermaxdevutils.utils.NetworkUtil

class SettingsPresenter(private val context: Context, private val mainView: SettingsView.MainView) :
    SettingsView.Presenter {
    override fun getSettings(url: String) {
        if (NetworkUtil.instance!!.isNetworkAvailable(context)) {
            mainView.onStartProgress()
            AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .setTag("ADS_SETTINGS")
                .build()
                .getAsObject(
                    SettingsResponse::class.java,
                    object : ParsedRequestListener<SettingsResponse> {
                        override fun onResponse(response: SettingsResponse) {
                            mainView.onStopProgress()
                            mainView.onSettingsLoaded(response.settings)
                        }

                        override fun onError(anError: ANError?) {
                            mainView.onStopProgress()
                            mainView.onFailed(anError?.message!!)
                        }
                    })

        } else {
            mainView.onOffline()
        }
    }
}