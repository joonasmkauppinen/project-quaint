package com.newgat.quaint.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newgat.quaint.data.network.response.GooglePlacesAutocompleteResponse
import com.newgat.quaint.internal.NoConnectivityException

class GooglePlacesDataSourceImpl(
    private val googlePlacesApiService: GooglePlacesApiService
) : GooglePlacesDataSource {

    private val _currentInputPredictions = MutableLiveData<GooglePlacesAutocompleteResponse>()
    override val currentInputPredictions: LiveData<GooglePlacesAutocompleteResponse>
        get() = _currentInputPredictions

    override suspend fun fetchInputPredictions(input: String) {
        try {
            val fetchedInputPredictions = googlePlacesApiService
                .getAddressAutocompletePredictions(input)
                .await()
            _currentInputPredictions.postValue(fetchedInputPredictions)
        } catch (e: NoConnectivityException) {
            Log.e("GooglePlacesDataSource", "No internet connection. ", e)
        }
    }
}