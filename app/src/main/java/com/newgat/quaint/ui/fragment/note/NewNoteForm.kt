package com.newgat.quaint.ui.fragment.note

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import com.newgat.quaint.R
import kotlinx.android.synthetic.main.new_note_form_fragment.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewNoteForm : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: NewNoteFormViewModelFactory by instance()

    private lateinit var rootView: View
    private lateinit var viewModel: NewNoteFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.new_note_form_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewNoteFormViewModel::class.java)
        // TODO: Use the ViewModel

        val placeArray = ArrayList<String>().apply {
            add("Add place (optional)")
            add("Home")
            add("School")
            add("Work")
            add("Mall")
        }
        val adapter = PlacesSpinnerAdapter(context!!, placeArray)
        rootView.notePlaceDropdown.adapter = adapter
    }



}
