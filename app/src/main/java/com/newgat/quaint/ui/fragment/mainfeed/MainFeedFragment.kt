package com.newgat.quaint.ui.fragment.mainfeed

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.newgat.quaint.R
import com.newgat.quaint.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.locations_section_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFeedFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: MainFeedViewModelFactory by instance()

    private lateinit var viewModel: MainFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.locations_section_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainFeedViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        val locations = viewModel.locations.await()
        locations.observe(this@MainFeedFragment, Observer {
            if (it.isEmpty()) {
                locationsTv.text = "No Locations added"
                return@Observer
            } else {
                locationsTv.text = it.toString()
            }
        })

        val notes = viewModel.notes.await()
        notes.observe(this@MainFeedFragment, Observer {
            if (it.isEmpty()) {
                notesTv.text = "No notes added"
            } else {
                notesTv.text = it.toString()
            }
        })
    }

}
