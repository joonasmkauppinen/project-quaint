package com.newgat.quaint.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newgat.quaint.data.network.response.GoogleGeocodingResponse
import com.newgat.quaint.internal.NoConnectivityException

class GoogleGeocodingDataSourceImpl(
    private val googleGeocodingService: GoogleGeocodingService
) : GoogleGeocodingDataSource {

    private val _downloadedGeocodingResult = MutableLiveData<GoogleGeocodingResponse>()
    override val downloadedGeocodingResult: LiveData<GoogleGeocodingResponse>
        get() = _downloadedGeocodingResult

    override suspend fun fetchGeocodingForAddress(placeId: String) {
        try {
            val fetchedGeocodingResponse = googleGeocodingService
                .getGeocodingForAddress(placeId)
                .await()
            _downloadedGeocodingResult.postValue(fetchedGeocodingResponse)
        } catch (e: NoConnectivityException) {
            Log.e("GeocodingDataSource", "No internet connection. ", e)
        }
    }
}