package com.newgat.quaint.ui.fragment.note

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged

import com.newgat.quaint.R
import com.newgat.quaint.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.new_note_form_fragment.view.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewNoteForm : ScopedFragment(), KodeinAware {

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

        bindUI()
    }

    private fun bindUI() = launch {
        val placeArray = viewModel.userPlaces.await()
        val adapter = PlacesSpinnerAdapter(context!!, placeArray)
        rootView.notePlaceDropdown.adapter = adapter
        rootView.notePlaceDropdown.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) return
                // Use adjusted position because PlaceSpinnerAdapter adds placeholder item at the
                // beginning of the spinner
                val adjustedPosition = position - 1
                val selectedLocationName = placeArray[adjustedPosition]
                viewModel.setNewNoteLocationName(selectedLocationName)
            }
        }

        rootView.newNoteTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.setNewNoteTitle("$text")
        }

        rootView.newNoteContent.doOnTextChanged { text, _, _, _ ->
            viewModel.setNewNoteContent("$text")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearNewNoteFields()
    }
}
