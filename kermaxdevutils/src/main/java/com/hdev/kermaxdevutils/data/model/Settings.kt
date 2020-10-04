package com.hdev.kermaxdevutils.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Settings(
    @SerializedName("admob_banner_id") val admob_banner_id: String,
    @SerializedName("admob_interstitial_id") val admob_interstitial_id: String,
    @SerializedName("fan_banner_id") val fan_banner_id: String,
    @SerializedName("hpk") val hpk: String,
    @SerializedName("fan_interstitial_id") val fan_interstitial_id: String,
    @SerializedName("switch_to") val switch_to: String,
    @SerializedName("update") val update: Update
) : Parcelable