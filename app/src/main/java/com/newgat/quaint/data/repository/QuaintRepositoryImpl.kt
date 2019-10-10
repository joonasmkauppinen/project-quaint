package com.newgat.quaint.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newgat.quaint.data.db.LocationsDao
import com.newgat.quaint.data.db.NotesDao
import com.newgat.quaint.data.db.entity.*
import com.newgat.quaint.data.network.GoogleGeocodingDataSource
import com.newgat.quaint.data.network.GooglePlacesDataSource
import com.newgat.quaint.data.network.response.GoogleGeocodingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class QuaintRepositoryImpl(
    private val notesDao: NotesDao,
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

    override fun clearCurrentlySelectedAddress() {
        _currentSelectedAddress.postValue(null)
    }

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

    override fun clearCurrentPlacePredictions() {
        _currentPlacePredictions.postValue(emptyList())
    }

    override fun fetchPlacePredictionsForInput(input: String) {
        GlobalScope.launch(Dispatchers.IO) {
            googlePlacesDataSource.fetchInputPredictions(input)
        }
    }


    private var _currentNewNoteTitle: String? = null
    private var _currentNewNoteLocationName: String? = null
    private var _currentNewNoteContent: String? = null

    override fun setNewCurrentNewNoteTitle(title: String) {
        _currentNewNoteTitle = title
    }

    override fun setNewCurrentNewNoteLocationName(name: String) {
        _currentNewNoteLocationName = name
        GlobalScope.launch(Dispatchers.IO) {
            val locationName = _currentNewNoteLocationName!!
            val id = locationsDao.getIdForLocation(locationName)
            Log.d("Repository", "Id for $locationName: $id")
        }
    }

    override fun setNewCurrentNewNoteContent(content: String) {
        _currentNewNoteContent = content
    }

    override fun clearCurrentNewNoteFields() {
        _currentNewNoteTitle = null
        _currentNewNoteLocationName =  null
        _currentNewNoteContent = null
    }

    override fun insertNote() {
        GlobalScope.launch(Dispatchers.IO) {
            val title = _currentNewNoteTitle
            val locationName = _currentNewNoteLocationName
            val locationId = if (locationName != null) locationsDao.getIdForLocation(locationName) else null
            val content = _currentNewNoteContent
            val newNoteEntry = UserNoteEntry(title, content, locationId, locationName)
            notesDao.insert(newNoteEntry)
        }
    }

    override suspend fun getNotesForLocation(locationId: Int): LiveData<List<UserNoteEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext notesDao.getNotesForLocation(locationId)
        }
    }

    override suspend fun getNotesList(): LiveData<List<UserNoteEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext notesDao.getAllNotes()
        }
    }

    override suspend fun getLocationsList(): LiveData<List<UserLocationEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext locationsDao.getAllLocations()
        }
    }

    override suspend fun getLocationNames(): List<String> {
        return withContext(Dispatchers.IO) {
            return@withContext locationsDao.getAllLocationNames()
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