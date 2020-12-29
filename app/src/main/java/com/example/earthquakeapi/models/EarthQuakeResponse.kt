package com.example.earthquakeapi.models

import com.google.gson.annotations.SerializedName

data class EarthQuakeResponse(
    @SerializedName("features")
    var quakeItems: List<EarthQuakeAtt>
) {
}