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
}
