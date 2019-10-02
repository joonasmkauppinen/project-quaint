package com.newgat.quaint.data.repository

import androidx.lifecycle.LiveData
import com.newgat.quaint.data.db.LocationsDao
import com.newgat.quaint.data.db.entity.LocationEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuaintRepositoryImpl(
    private val locationsDao: LocationsDao
) : QuaintRepository {
    override suspend fun getLocationsList(): LiveData<List<LocationEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext locationsDao.getAllLocations()
        }
    }
}