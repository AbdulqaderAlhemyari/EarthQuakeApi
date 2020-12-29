package com.example.earthquakeapi.models

import com.google.gson.annotations.SerializedName

data class EarthQuakeAtt(
    @SerializedName("properties")
    var properties: EarthQuakeProperties= EarthQuakeProperties(),

    @SerializedName("geometry")
    var geometric:EarthQuakeGeometric = EarthQuakeGeometric()
) {
}