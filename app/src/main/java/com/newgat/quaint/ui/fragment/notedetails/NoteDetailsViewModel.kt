package com.newgat.quaint.ui.fragment.notedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.newgat.quaint.data.db.entity.UserNoteEntry
import com.newgat.quaint.data.repository.QuaintRepository
import com.newgat.quaint.internal.lazyDeferred

class NoteDetailsViewModel(
    val repository: QuaintRepository
) : ViewModel() {
    val noteDetails by lazyDeferred {
        repository.getSelectedNoteDetails()
    }
}