package com.newgat.quaint.ui.fragment.locationssection

import androidx.lifecycle.ViewModel
import com.newgat.quaint.data.db.entity.UserLocationEntry
import com.newgat.quaint.data.repository.QuaintRepository
import com.newgat.quaint.internal.lazyDeferred

class LocationsSectionViewModel(
    private val repository: QuaintRepository
) : ViewModel() {
    val locations by lazyDeferred {
        repository.getLocationsList()
    }
}
