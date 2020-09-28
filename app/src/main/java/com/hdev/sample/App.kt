package com.hdev.sample

import androidx.multidex.MultiDexApplication
import com.hdev.kermaxdevutils.data.remote.KermaxDevUtils

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        KermaxDevUtils.instance!!.initAdmob(applicationContext)
    }
}