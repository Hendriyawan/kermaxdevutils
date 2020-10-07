package com.hdev.kermaxdevutils.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Update(
    @SerializedName("version_name") val versionName: String,
    @SerializedName("version_code") val versionCode: Int,
    @SerializedName("changelog") val changelog: String,
    @SerializedName("link") val link : String,
    @SerializedName("moved_godev") val moved_godev : Boolean
) : Parcelable