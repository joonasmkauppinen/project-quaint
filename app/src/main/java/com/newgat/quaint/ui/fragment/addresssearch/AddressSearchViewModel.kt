package com.newgat.quaint.ui.fragment.addresssearch

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newgat.quaint.data.repository.QuaintRepository

class AddressSearchViewModel(
    private val repository: QuaintRepository
) : ViewModel() {

    val currentAddressContent: LiveData<String>
        get() = repository.currentPlaceNameInput


    val editTextContent = MutableLiveData<String>()

    fun onEditTextChange() {
        repository.setCurrentPlaceName(editTextContent.value!!)
    }

    fun clearPlaceSearchFromRepository() {
        repository.clearCurrentPlaceEntry()
    }

    fun clearAddressInput() {
        editTextContent.value = ""
    }

}
