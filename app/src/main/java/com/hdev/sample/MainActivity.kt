package com.hdev.sample

import android.os.Bundle
import com.hdev.kermaxdevutils.activity.BaseKermaxDevActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseKermaxDevActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBannerAds(
            layout_banner,
            "insurance,btc,car,trading",
            "ca-app-pub-3940256099942544/6300978111"
        )
        initInterstitialAd("ca-app-pub-3940256099942544/1033173712", "insurance,btc,car,trading")


        //show interstitial
        button_show_interstitial.setOnClickListener {
            showInterstitial()
        }

        //Code snippet for force app update BottomSheetDialog
        /*

        val update = Update(
            "1.4.0",
            2,
            "* Feature download wallpaper\n* More 500+ wallpaper\n* HD Wallpaper",
            "https://google.com"
        )

        val bottomSheetDialog = BottomSheetDialogUpdate()
        bottomSheetDialog.setData(update)
        bottomSheetDialog.setOnButtonClick(object : BottomSheetDialogUpdate.OnButtonClick {
            override fun onButtonClose() {
                finish()
            }

            override fun onUpdate(link: String) {

            }
        })
        val latestVersionCode = 1
        val newVersionCode = update.versionCode
        if (latestVersionCode < newVersionCode) {
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog::class.java.simpleName)
        }*/

        //Code snippet for in app update usage
        /*
        KDAppUpdateManager.Builder(this)!!.resumeUpdates(true)
            .mode(Constants.UpdateMode.FLEXIBLE)
            .snackMessage("An update has just been downloaded")
            .snackBarAction("Restart")
            .callback(object : KDAppUpdateManager.AppUpdateCallback {
                override fun onAppUpdateStatus(status: AppUpdateStatus) {
                }

                override fun onUpdateError(code: Int, error: Throwable) {
                }
            })
            .checkAppUpdate()*/

    }

}