package com.newgat.quaint.ui.fragment.bottomsheetmodal

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context
import android.os.Handler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.newgat.quaint.R
import com.newgat.quaint.internal.ActionType
import kotlinx.android.synthetic.main.bottom_sheet_modal_fragment.*
import kotlinx.android.synthetic.main.bottom_sheet_modal_fragment.view.*
import java.lang.ClassCastException
import kotlin.concurrent.thread

class BottomSheetModalFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var listener: BottomSheetListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as BottomSheetListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement BottomSheetListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_modal_fragment, container, false)
        view.listItemNote.setOnClickListener(this)
        view.listItemLocation.setOnClickListener(this)
        return view
    }

    interface BottomSheetListener {
        fun onActionClicked(action: ActionType)
    }

    override fun onClick(v: View?) {
        when (v) {
            listItemNote -> listener.onActionClicked(ActionType.NOTE)
            listItemLocation -> listener.onActionClicked(ActionType.LOCATION)
        }
        Handler().postDelayed({dismiss()}, 300)
    }
}
