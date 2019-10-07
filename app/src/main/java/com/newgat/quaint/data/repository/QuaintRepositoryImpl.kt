package com.newgat.quaint.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newgat.quaint.data.db.LocationsDao
import com.newgat.quaint.data.db.entity.LocationEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuaintRepositoryImpl(
    private val locationsDao: LocationsDao
) : QuaintRepository {

    private val _currentPlaceNameInput = MutableLiveData<String>()
    override val currentPlaceNameInput: LiveData<String>
        get() = _currentPlaceNameInput

    init {
        _currentPlaceNameInput.value = ""
    }

    override fun setCurrentPlaceName(name: String) {
        _currentPlaceNameInput.value = name
        Log.d("Repository", "_currentPlaceNameInput:  ${_currentPlaceNameInput.value}")
    }

    override fun clearCurrentPlaceEntry() {
        _currentPlaceNameInput.value = ""
        Log.d("Repository", "_currentPlaceNameInput:  ${_currentPlaceNameInput.value}")
    }

    override suspend fun getLocationsList(): LiveData<List<LocationEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext locationsDao.getAllLocations()
        }
    }

    override fun insertLocation(location: LocationEntry) {
        GlobalScope.launch(Dispatchers.IO) {
            locationsDao.insert(location)
        }
    }
}