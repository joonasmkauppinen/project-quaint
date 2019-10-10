package com.newgat.quaint.data.repository

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.db.entity.UserLocationEntry
import com.newgat.quaint.data.db.entity.Prediction

interface QuaintRepository {
    // For address search fragment
    fun setCurrentPlaceNameInput(placeName: String)
    val currentSelectedAddress: LiveData<Prediction>
    fun setNewCurrentSelectedAddress(prediction: Prediction)
    fun clearCurrentlySelectedAddress()
    val currentPlacePredictions: LiveData<List<Prediction>>
    fun fetchPlacePredictionsForInput(input: String)
    fun clearCurrentPlacePredictions()

    // For locations section
    suspend fun getLocationsList(): LiveData<List<UserLocationEntry>>
    suspend fun getLocationNames(): List<String>
    fun insertLocation()
}