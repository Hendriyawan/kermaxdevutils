package com.hdev.kermaxdevutils.activity

import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAdListener
import com.google.android.gms.ads.*
import com.hdev.kermaxdevutils.BuildConfig

open class BaseKermaxDevActivity : AppCompatActivity() {
    private val TAG = BaseKermaxDevActivity::class.java.simpleName

    private var interstitialAd: InterstitialAd? = null
    private lateinit var fanBannerAds : com.facebook.ads.AdView
    private lateinit var fanInterstitialAds : com.facebook.ads.InterstitialAd

    /**
     * initialize banner ads
     * @param containerView layout to display banner ads
     * @param keywords hpk (height keyword payout)
     * @param bannerId unit ad banner
     */
    protected fun initBannerAds(containerView: LinearLayout, keywords : String, bannerId : String){
        val keyword = keywords.split(",")
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = bannerId
        containerView.addView(adView)
        adView.loadAd(
            AdRequest.Builder()
                .addKeyword(keyword[0])
                .addKeyword(keyword[1])
                .addKeyword(keyword[2])
                .addKeyword(keyword[3]).build())

        //listener
        adView.adListener = object : AdListener(){
            override fun onAdLoaded() {
                containerView.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                containerView.visibility = View.GONE
            }

            override fun onAdOpened() {
                containerView.visibility = View.VISIBLE
            }

            override fun onAdClosed() {
                adView.loadAd(AdRequest.Builder()
                    .addKeyword(keyword[0])
                    .addKeyword(keyword[1])
                    .addKeyword(keyword[2])
                    .addKeyword(keyword[3]).build())
            }
        }
    }

    /**
     * initialize Facebook Audience Network BANNER
     * @param containerView layout to display banner ads
     * @param placementId id placement of banner ad
     */
    protected fun initBannerAdsFan(containerView : LinearLayout, placementId : String){
        val placement = if (BuildConfig.DEBUG) "IMG_16_9_APP_INSTALL#$placementId" else placementId
        fanBannerAds =
            com.facebook.ads.AdView(this, placement, com.facebook.ads.AdSize.BANNER_HEIGHT_50)
        containerView.addView(fanBannerAds)
        fanBannerAds.setAdListener(object : com.facebook.ads.AdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                containerView.visibility = View.GONE
                Log.d(TAG, "fan banner onError")
            }

            override fun onAdLoaded(p0: Ad?) {
                Log.d(TAG, "fan banner onLoaded")
                containerView.visibility = View.VISIBLE
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d(TAG, "fan banner onClicked")
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d(TAG, "fan banner onLoggingImpression")
            }
        })
        fanBannerAds.loadAd()
    }

    /**
     *
     * initialize Interstitial ads
     * @param interstitialId unit ad interstitial
     * @param keywords hpk (height keyword payout)
     */
    protected fun initInterstitialAd(interstitialId : String, keywords: String){
        val keyword = keywords.split(",")
        interstitialAd = InterstitialAd(this)
        interstitialAd!!.adUnitId = interstitialId
        interstitialAd!!.loadAd(
            AdRequest.Builder()
                .addKeyword(keyword[0])
                .addKeyword(keyword[1])
                .addKeyword(keyword[2])
                .addKeyword(keyword[3]).build()
        )
        interstitialAd!!.adListener = object : AdListener (){
            override fun onAdClosed() {
                interstitialAd!!.loadAd(AdRequest.Builder()
                    .addKeyword(keyword[0])
                    .addKeyword(keyword[1])
                    .addKeyword(keyword[2])
                    .addKeyword(keyword[3]).build())
            }
        }
    }

    /**
     * showing interstitial ad
     */
    protected fun showInterstitial(){
        if(interstitialAd != null && interstitialAd!!.isLoaded){
            interstitialAd!!.show()
        }
    }


    /**
     * initialize Facebook Audience Network interstitial
     * @param placementId interstitial placement id
     */
    protected fun initInterstitialAdsFan(placementId: String) {
        val placement = if (BuildConfig.DEBUG) "IMG_16_9_APP_INSTALL#$placementId" else placementId
        fanInterstitialAds = com.facebook.ads.InterstitialAd(this, placement)
        fanInterstitialAds.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(p0: Ad?) {
                Log.d(TAG, "fan interstitial onInterstitialDisplayed")
            }

            override fun onInterstitialDismissed(p0: Ad?) {
                fanInterstitialAds.loadAd()
                Log.d(TAG, "fan interstitial onInterstitialDismissed")
            }

            override fun onError(p0: Ad?, p1: AdError?) {
                Log.d(TAG, "fan interstitial onError")
            }

            override fun onAdLoaded(p0: Ad?) {
                Log.d(TAG, "fan intesrstitial onAdLoaded")
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d(TAG, "fan interstitial onAdClicked")
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d(TAG, "fan interstitial onLoggingImpression")
            }
        })
        fanInterstitialAds.loadAd()
    }

    /**
     * showing interstitial ad facebook audience network
     */
    protected fun showInterstitialFan() {
        if (!fanInterstitialAds.isAdLoaded) {
            return
        }
        if (fanInterstitialAds.isAdInvalidated) {
            return
        }
        fanInterstitialAds.show()
    }
}