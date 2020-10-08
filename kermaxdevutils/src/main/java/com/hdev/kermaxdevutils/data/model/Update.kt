package com.hdev.kermaxdevutils.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Update(
    @SerializedName("version_name") val versionName: String = "1.0",
    @SerializedName("version_code") val versionCode: Int = 1,
    @SerializedName("changelog") val changelog: String = "changelog 1,changelog 2,changelog 3",
    @SerializedName("link") val link: String = "https://google.com",
    @SerializedName("moved_godev") val moved_godev: Boolean = false
) : Parcelable {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}