package com.example.earthquakeapi.models

import com.google.gson.annotations.SerializedName

data class EarthQuakeGeometric(
    @SerializedName("coordinates")
    var geometrics: List<Double> = emptyList()
)