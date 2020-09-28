package com.hdev.kermaxdevutils.data.remote

import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.hdev.kermaxdevutils.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class FastAndroidNetworking {
    fun init(context: Context) {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).build()
        AndroidNetworking.initialize(context, client)
    }

    companion object {
        private var fastAndroidNetworkingHelper: FastAndroidNetworking? = null
        @get:Synchronized
        val instance: FastAndroidNetworking?
            get() {
                if (fastAndroidNetworkingHelper == null) {
                    fastAndroidNetworkingHelper = FastAndroidNetworking()
                }
                return fastAndroidNetworkingHelper
            }
    }
}