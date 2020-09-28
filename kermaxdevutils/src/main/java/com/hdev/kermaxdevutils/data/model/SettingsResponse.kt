package com.hdev.kermaxdevutils.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SettingsResponse(
    @SerializedName("settings") val settings: Settings
) : Parcelable