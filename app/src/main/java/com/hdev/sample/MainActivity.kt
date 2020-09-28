package com.hdev.sample

import android.os.Bundle
import com.hdev.kermaxdevutils.activity.BaseKermaxDevActivity
import com.hdev.kermaxdevutils.utils.ReviewHelper
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

        button_start_review.setOnClickListener {
            ReviewHelper.instance!!.askForReview(this)
        }

        //show interstitial
        button_show_interstitial.setOnClickListener {
            showInterstitial()
        }
    }
}