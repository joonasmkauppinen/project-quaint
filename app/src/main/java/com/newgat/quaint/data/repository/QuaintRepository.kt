package com.newgat.quaint.data.repository

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.db.entity.LocationEntry

interface QuaintRepository {
    suspend fun getLocationsList(): LiveData<List<LocationEntry>>
}