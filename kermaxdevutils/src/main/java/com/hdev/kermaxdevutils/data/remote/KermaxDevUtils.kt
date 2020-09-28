package com.hdev.kermaxdevutils.data.remote

import android.content.Context
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.MobileAds

class KermaxDevUtils {
    //initialize android mobile ads
    fun initAdmob(context: Context) {
        MobileAds.initialize(context)
    }

    //initialize facebook audience network
    fun initFan(context: Context) {
        AudienceNetworkAds.initialize(context)
    }

    companion object {
        private var adsHelper: KermaxDevUtils? = null
        @get:Synchronized
        val instance: KermaxDevUtils?
            get() {
                if (adsHelper == null) {
                    adsHelper = KermaxDevUtils()
                }
                return adsHelper
            }
    }
}