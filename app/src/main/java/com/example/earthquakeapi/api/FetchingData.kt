package com.example.earthquakeapi.api

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.earthquakeapi.models.EarthQuakeAtt
import com.example.earthquakeapi.models.EarthQuakeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchingData {
     var earthQuakeApi : APi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        earthQuakeApi = retrofit.create(APi::class.java)
    }

    fun getResponse(): LiveData<List<EarthQuakeAtt>>
    {   val responseLiveData: MutableLiveData<List<EarthQuakeAtt>> = MutableLiveData()
        val earthQuakeRequest : Call<EarthQuakeResponse> = earthQuakeApi.getResponse()

        earthQuakeRequest.enqueue(object : Callback<EarthQuakeResponse>{
            override fun onFailure(call: Call<EarthQuakeResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<EarthQuakeResponse>,
                response: Response<EarthQuakeResponse>
            ) {
                val response: EarthQuakeResponse? = response.body()
                var earthQuakeAtt: List<EarthQuakeAtt> = response?.quakeItems
                    ?: mutableListOf()
                responseLiveData.value = earthQuakeAtt
            }

        })
        return responseLiveData
    }
}