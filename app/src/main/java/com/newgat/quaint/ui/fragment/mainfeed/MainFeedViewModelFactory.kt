package com.newgat.quaint.ui.fragment.mainfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newgat.quaint.data.repository.QuaintRepository

class MainFeedViewModelFactory(
    private val repository: QuaintRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainFeedViewModel(repository) as T
    }
}