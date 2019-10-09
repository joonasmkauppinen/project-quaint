package com.newgat.quaint.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newgat.quaint.data.db.LocationsDao
import com.newgat.quaint.data.db.entity.LocationEntry
import com.newgat.quaint.data.db.entity.Prediction
import com.newgat.quaint.data.network.GooglePlacesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuaintRepositoryImpl(
    private val locationsDao: LocationsDao,
    private val googlePlacesDataSource: GooglePlacesDataSource
) : QuaintRepository {

    init {
        googlePlacesDataSource.downloadedInputPredictions.observeForever { newPredictions ->
            _currentPlacePredictions.postValue(newPredictions.predictions)
        }
    }

    private val _currentPlacePredictions = MutableLiveData<List<Prediction>>()
    override val currentPlacePredictions: LiveData<List<Prediction>>
        get() = _currentPlacePredictions

    override fun fetchPlacePredictionsForInput(input: String) {
        GlobalScope.launch(Dispatchers.IO) {
            googlePlacesDataSource.fetchInputPredictions(input)
        }
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