package com.example.earthquakeapi.models

import com.google.gson.annotations.SerializedName

data class EarthQuakeProperties(
    @SerializedName("place")
    var place: String = "",
    @SerializedName("time")
    var time: Long  = 0L,
    @SerializedName("mag")
    var strength : String = ""
) {
}