package com.newgat.quaint.ui.activity

import androidx.lifecycle.ViewModel
import com.newgat.quaint.data.repository.QuaintRepository

class NewActionViewModel(
    private val repository: QuaintRepository
) : ViewModel() {
    fun saveUserPlace() {
        repository.insertLocation()
    }
    fun saveUserNote() {
        repository.insertNote()
    }
}