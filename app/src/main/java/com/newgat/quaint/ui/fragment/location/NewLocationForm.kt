package com.newgat.quaint.ui.fragment.location

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newgat.quaint.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.address_search_fragment.view.*
import kotlinx.android.synthetic.main.new_location_form_fragment.view.*
import java.lang.ClassCastException


class NewLocationForm : Fragment() {

    private lateinit var listener: NewLocationFormListener
    private lateinit var viewModel: NewLocationFormViewModel

//    companion object {
//        fun newInstance() = NewLocationForm()
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewLocationFormListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement NewLocationFormListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_location_form_fragment, container, false)
        view.addressListItem.setOnClickListener { listener.openAddressSearch() }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewLocationFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

    interface NewLocationFormListener {
        fun openAddressSearch()
    }

}
