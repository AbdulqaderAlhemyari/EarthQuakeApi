package com.example.earthquakeapi.api

import com.example.earthquakeapi.models.EarthQuakeResponse
import retrofit2.Call
import retrofit2.http.GET

interface APi {

    @GET("fdsnws/event/1/query?format=geojson&limit=10")
    fun getResponse(): Call<EarthQuakeResponse>
}