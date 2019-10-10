package com.newgat.quaint.data.repository

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.db.entity.UserLocationEntry
import com.newgat.quaint.data.db.entity.Prediction
import com.newgat.quaint.data.db.entity.UserNoteEntry

interface QuaintRepository {
    // Address search
    fun setCurrentPlaceNameInput(placeName: String)
    val currentSelectedAddress: LiveData<Prediction>
    fun setNewCurrentSelectedAddress(prediction: Prediction)
    fun clearCurrentlySelectedAddress()
    val currentPlacePredictions: LiveData<List<Prediction>>
    fun fetchPlacePredictionsForInput(input: String)
    fun clearCurrentPlacePredictions()

    // Locations
    suspend fun getLocationsList(): LiveData<List<UserLocationEntry>>
    suspend fun getLocationNames(): List<String>
    fun insertLocation()

    // Note
    fun insertNote()
    suspend fun getNotesForLocation(locationId: Int): LiveData<List<UserNoteEntry>>
    suspend fun getNotesList(): LiveData<List<UserNoteEntry>>
    fun setNewCurrentNewNoteTitle(title: String)
    fun setNewCurrentNewNoteLocationName(name: String)
    fun setNewCurrentNewNoteContent(content: String)
    fun clearCurrentNewNoteFields()
}