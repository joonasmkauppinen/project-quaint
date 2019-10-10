package com.newgat.quaint.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newgat.quaint.data.repository.QuaintRepository

class NewActionViewModelFactory(
    private val repository: QuaintRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewActionViewModel(repository) as T
    }
}