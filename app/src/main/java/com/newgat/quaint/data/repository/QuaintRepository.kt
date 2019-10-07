package com.newgat.quaint.data.repository

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.db.entity.LocationEntry

interface QuaintRepository {
    val currentPlaceNameInput: LiveData<String>
    fun setCurrentPlaceName(name: String)

    suspend fun getLocationsList(): LiveData<List<LocationEntry>>
    fun insertLocation(location: LocationEntry)
}