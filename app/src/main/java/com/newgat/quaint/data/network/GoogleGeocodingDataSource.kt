package com.newgat.quaint.data.network

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.network.response.GoogleGeocodingResponse

interface GoogleGeocodingDataSource {
    val downloadedGeocodingResult: LiveData<GoogleGeocodingResponse>

    suspend fun fetchGeocodingForAddress(placeId: String)
}