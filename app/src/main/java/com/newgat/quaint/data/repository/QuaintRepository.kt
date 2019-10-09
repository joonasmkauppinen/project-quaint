package com.newgat.quaint.data.repository

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.db.entity.LocationEntry
import com.newgat.quaint.data.db.entity.Prediction

interface QuaintRepository {
    // For address search fragment
    val currentPlacePredictions: LiveData<List<Prediction>>
    fun fetchPlacePredictionsForInput(input: String)

    // For locations section
    suspend fun getLocationsList(): LiveData<List<LocationEntry>>
    fun insertLocation(location: LocationEntry)
}