package com.newgat.quaint.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newgat.quaint.data.network.response.GooglePlacesAutocompleteResponse
import com.newgat.quaint.internal.NoConnectivityException

class GooglePlacesDataSourceImpl(
    private val googlePlacesApiService: GooglePlacesApiService
) : GooglePlacesDataSource {

    private val _downloadedInputPredictions = MutableLiveData<GooglePlacesAutocompleteResponse>()
    override val downloadedInputPredictions: LiveData<GooglePlacesAutocompleteResponse>
        get() = _downloadedInputPredictions

    override suspend fun fetchInputPredictions(input: String) {
        try {
            val fetchedInputPredictions = googlePlacesApiService
                .getAddressAutocompletePredictions(input)
                .await()
            _downloadedInputPredictions.postValue(fetchedInputPredictions)
        } catch (e: NoConnectivityException) {
            Log.e("GooglePlacesDataSource", "No internet connection. ", e)
        }
    }
}