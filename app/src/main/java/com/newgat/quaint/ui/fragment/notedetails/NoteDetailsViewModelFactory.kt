package com.newgat.quaint.ui.fragment.notedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newgat.quaint.data.repository.QuaintRepository

class NoteDetailsViewModelFactory(
    val repository: QuaintRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteDetailsViewModel(repository) as T
    }
}