package com.newgat.quaint.ui.fragment.notedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newgat.quaint.R
import com.newgat.quaint.data.db.entity.UserNoteEntry
import com.newgat.quaint.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.note_details.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NoteDetailsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: NoteDetailsViewModelFactory by instance()

    private lateinit var viewModel: NoteDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.note_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NoteDetailsViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        val noteDetails = viewModel.noteDetails.await()
        noteDetails.observe(this@NoteDetailsFragment, Observer {
            it.first().apply {
                noteDetailTitle.text = this.title
                noteDetailContent.text = this.content
                if (this.note_location_name == null) {
                    noteDetailLocaion.visibility = View.GONE
                } else {
                    noteDetailLocaion.text = this.note_location_name
                }
            }
        })
    }
}
