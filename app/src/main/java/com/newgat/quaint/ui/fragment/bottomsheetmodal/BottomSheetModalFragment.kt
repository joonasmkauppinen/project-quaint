package com.newgat.quaint.ui.fragment.bottomsheetmodal

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.newgat.quaint.R

class BottomSheetModalFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: BottomSheetModalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_modal_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BottomSheetModalViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
