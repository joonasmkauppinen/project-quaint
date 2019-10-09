package com.newgat.quaint.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newgat.quaint.data.db.LocationsDao
import com.newgat.quaint.data.db.entity.Location
import com.newgat.quaint.data.db.entity.UserLocationEntry
import com.newgat.quaint.data.db.entity.Prediction
import com.newgat.quaint.data.db.entity.Result
import com.newgat.quaint.data.network.GoogleGeocodingDataSource
import com.newgat.quaint.data.network.GooglePlacesDataSource
import com.newgat.quaint.data.network.response.GoogleGeocodingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class QuaintRepositoryImpl(
    private val locationsDao: LocationsDao,
    private val googlePlacesDataSource: GooglePlacesDataSource,
    private val googleGeocodingDataSource: GoogleGeocodingDataSource
) : QuaintRepository {

    init {
        googlePlacesDataSource.downloadedInputPredictions.observeForever { newPredictions ->
            _currentPlacePredictions.postValue(newPredictions.predictions)
        }
        googleGeocodingDataSource.downloadedGeocodingResult.observeForever { geocodingResponse ->
            Log.d("Repository", "Geocoding response: $geocodingResponse")
            _geocodingForCurrentAddress = geocodingResponse
        }
    }

    private var _currentPlaceNameInput: String = ""
    override fun setCurrentPlaceNameInput(placeName: String) {
        _currentPlaceNameInput = placeName
    }

    private var _geocodingForCurrentAddress: GoogleGeocodingResponse? = null

    private val _currentSelectedAddress = MutableLiveData<Prediction>()
    override val currentSelectedAddress: LiveData<Prediction>
        get() = _currentSelectedAddress

    override fun setNewCurrentSelectedAddress(prediction: Prediction) {
        _currentSelectedAddress.postValue(prediction)
        GlobalScope.launch(Dispatchers.IO) {
            val placeId = prediction.place_id
            googleGeocodingDataSource.fetchGeocodingForAddress(placeId)
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

    override suspend fun getLocationsList(): LiveData<List<UserLocationEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext locationsDao.getAllLocations()
        }
    }

    override fun insertLocation() {
        val placeName = _currentPlaceNameInput
        val address: Prediction = _currentSelectedAddress.value as Prediction
        val coordinates: Location = _geocodingForCurrentAddress!!.results.first().geometry.location
        val newLocation = UserLocationEntry(placeName, address, coordinates)
        GlobalScope.launch(Dispatchers.IO) {
            locationsDao.insert(newLocation)
        }
    }
}