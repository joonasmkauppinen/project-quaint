package com.newgat.quaint.ui.fragment.location

import androidx.lifecycle.ViewModel
import com.newgat.quaint.data.repository.QuaintRepository

class NewLocationFormViewModel(
    repository: QuaintRepository
) : ViewModel() {
    val userSelectedAddress = repository.currentSelectedAddress
}
