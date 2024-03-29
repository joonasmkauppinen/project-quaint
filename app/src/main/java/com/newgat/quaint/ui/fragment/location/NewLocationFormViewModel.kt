package com.newgat.quaint.ui.fragment.location

import androidx.lifecycle.ViewModel
import com.newgat.quaint.data.repository.QuaintRepository

class NewLocationFormViewModel(
    val repository: QuaintRepository
) : ViewModel() {
    val userSelectedAddress = repository.currentSelectedAddress
    fun updatePlaceName(placeName: String) {
        repository.setCurrentPlaceNameInput(placeName)
    }
    fun clearInputFields() {
        repository.clearCurrentlySelectedAddress()
    }
}
