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
import com.newgat.quaint.data.db.entity.UserNoteEntry
import com.newgat.quaint.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_feed_fragment.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFeedFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: MainFeedViewModelFactory by instance()

    private lateinit var locationsSection: Section
    private lateinit var notesSection: Section
    private lateinit var paddingSection: Section
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
        notes.observe(this@MainFeedFragment, Observer {
            updateNotesSection(it)
        })
    }

    private fun initRecyclerView() {
        locationsSection = Section().apply {
            setHeader(SectionHeader(getString(R.string.feed_header_locations), false))
        }

        notesSection = Section().apply {
            setHeader(SectionHeader(getString(R.string.feed_header_notes), false))
        }

        paddingSection = Section().apply {
            setHeader(PaddingHeader())
        }

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            add(locationsSection)
            add(notesSection)
            add(paddingSection)
            setOnItemClickListener { item, view ->
                when (item) {
                    (item as? NoteItem) -> openNoteDetails(item)
                    (item as? LocationItem) -> openLocationDetails(item)
                    (item as? SectionHeader) -> openListView(item)
                }
            }
        }

        mainFeedRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainFeedFragment.context)
            adapter = groupAdapter
        }
    }

    private fun openNoteDetails(item: NoteItem) {
        toast("${item.userNoteEntry.title}")
    }

    private fun openLocationDetails(item: LocationItem) {
        toast(item.userLocationEntry.name)
    }

    private fun openListView(item: SectionHeader) {
        when (item.headerName) {
            getString(R.string.feed_header_locations) -> toast("All Locations")
            getString(R.string.feed_header_notes) -> toast("All Notes")
        }
    }

    private fun updateLocationsSection(items: List<UserLocationEntry>) {
        if (items.size > 3) {
            locationsSection.update(items.take(3).toLocationItems())
            locationsSection.setHeader(SectionHeader(getString(R.string.feed_header_locations), true))
        } else {
            locationsSection.update(items.toLocationItems())
        }
    }

    private fun List<UserLocationEntry>.toLocationItems() : List<LocationItem> {
        return this.map {
           LocationItem(it)
        }
    }

    private fun updateNotesSection(items: List<UserNoteEntry>) {
        if (items.size > 3) {
            notesSection.update(items.take(3).toNoteItems())
            notesSection.setHeader(SectionHeader(getString(R.string.feed_header_notes), true))
        } else {
            notesSection.update(items.toNoteItems())
        }

    }

    private fun List<UserNoteEntry>.toNoteItems() : List<NoteItem> {
        return this.map {
            NoteItem(it)
        }
    }

}
