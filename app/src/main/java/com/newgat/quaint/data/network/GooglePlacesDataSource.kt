package com.newgat.quaint.data.network

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.network.response.GooglePlacesAutocompleteResponse

interface GooglePlacesDataSource {
    val currentInputPredictions: LiveData<GooglePlacesAutocompleteResponse>

    suspend fun fetchInputPredictions(input: String)
}