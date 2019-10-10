package com.newgat.quaint.ui.fragment.note

import androidx.lifecycle.ViewModel
import com.newgat.quaint.data.repository.QuaintRepository
import com.newgat.quaint.internal.lazyDeferred

class NewNoteFormViewModel(
    val repository: QuaintRepository
) : ViewModel() {
    val userPlaces by lazyDeferred {
        repository.getLocationNames()
    }

    fun setNewNoteTitle(title: String) {
        repository.setNewCurrentNewNoteTitle(title)
    }

    fun setNewNoteLocationName(locationName: String) {
        repository.setNewCurrentNewNoteLocationName(locationName)
    }

    fun setNewNoteContent(content: String) {
        repository.setNewCurrentNewNoteContent(content)
    }

    fun clearNewNoteFields() {
        repository.clearCurrentNewNoteFields()
    }
}
