package com.example.earthquakeapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.earthquakeapi.api.FetchingData
import com.example.earthquakeapi.models.EarthQuakeAtt

class EarthQuakeViewModel : ViewModel() {

    var earthQuakeAttribute: LiveData<List<EarthQuakeAtt>> = FetchingData().getResponse()

}