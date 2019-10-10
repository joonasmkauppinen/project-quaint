package com.newgat.quaint.ui.fragment.addresssearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newgat.quaint.data.repository.QuaintRepository

class AddressSearchViewModelFactory(
    private val repository: QuaintRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddressSearchViewModel(repository) as T
    }
}