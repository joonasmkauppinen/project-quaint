package com.newgat.quaint.ui.fragment.mainfeed

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.newgat.quaint.R
import com.newgat.quaint.data.db.entity.UserLocationEntry
import com.newgat.quaint.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_feed_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFeedFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: MainFeedViewModelFactory by instance()

    private lateinit var locationsSection: Section
    private lateinit var viewModel: MainFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_feed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainFeedViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        initRecyclerView()
        val locations = viewModel.locations.await()
        locations.observe(this@MainFeedFragment, Observer {
            updateLocationsSection(it)
        })

        val notes = viewModel.notes.await()
//        notes.observe(this@MainFeedFragment, Observer {
//
//        })
    }

    private fun initRecyclerView() {
        locationsSection = Section().apply {
            setHeader(LocationHeader())
        }

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            add(locationsSection)
        }

        mainFeedRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainFeedFragment.context)
            adapter = groupAdapter
        }
    }

    private fun updateLocationsSection(items: List<UserLocationEntry>) {
        locationsSection.update(items.toAddressPredictionItems())
    }

    private fun List<UserLocationEntry>.toAddressPredictionItems() : List<LocationItem> {
        return this.map {
           LocationItem(it)
        }
    }

}
