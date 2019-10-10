package com.newgat.quaint.ui.fragment.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newgat.quaint.data.repository.QuaintRepository

class NewNoteFormViewModelFactory(
    val repository: QuaintRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewNoteFormViewModel(repository) as T
    }
}