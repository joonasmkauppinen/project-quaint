package com.newgat.quaint.data.repository

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.db.entity.UserLocationEntry
import com.newgat.quaint.data.db.entity.Prediction

interface QuaintRepository {
    // For address search fragment
    fun setCurrentPlaceNameInput(placeName: String)
    val currentSelectedAddress: LiveData<Prediction>
    fun setNewCurrentSelectedAddress(prediction: Prediction)
    val currentPlacePredictions: LiveData<List<Prediction>>
    fun fetchPlacePredictionsForInput(input: String)

    // For locations section
    suspend fun getLocationsList(): LiveData<List<UserLocationEntry>>
    fun insertLocation()
}