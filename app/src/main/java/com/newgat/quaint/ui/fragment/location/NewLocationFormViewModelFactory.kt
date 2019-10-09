package com.newgat.quaint.ui.fragment.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newgat.quaint.data.repository.QuaintRepository

class NewLocationFormViewModelFactory(
    private val repository: QuaintRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewLocationFormViewModel(repository) as T
    }
}
