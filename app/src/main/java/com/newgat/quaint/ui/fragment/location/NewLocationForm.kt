package com.newgat.quaint.ui.fragment.location

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newgat.quaint.R


class NewLocationForm : Fragment() {

//    companion object {
//        fun newInstance() = NewLocationForm()
//    }

    private lateinit var viewModel: NewLocationFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_location_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewLocationFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
