package com.newgat.quaint.ui.fragment.addresssearch

import androidx.lifecycle.ViewModel
import com.newgat.quaint.data.db.entity.Prediction
import com.newgat.quaint.data.repository.QuaintRepository

class AddressSearchViewModel(
    private val repository: QuaintRepository
) : ViewModel() {

    val addressPredictions = repository.currentPlacePredictions

    fun onEditTextChange(input: String) {
        repository.fetchPlacePredictionsForInput(input)
    }

    fun onAddressSelected(prediction: Prediction) {
        repository.setNewCurrentSelectedAddress(prediction)
    }

    fun clearAddressPredictionList() {
        repository.clearCurrentPlacePredictions()
    }

}
