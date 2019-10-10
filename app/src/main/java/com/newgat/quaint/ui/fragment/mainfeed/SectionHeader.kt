package com.newgat.quaint.ui.fragment.mainfeed

import android.view.View
import com.newgat.quaint.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.main_feed_section_header.*

class SectionHeader(
    val headerName: String,
    private val showMoreButton: Boolean
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            sectionHeader.text = headerName
            if (showMoreButton) {
                seeAll.visibility = View.VISIBLE
            } else {
                seeAll.visibility = View.INVISIBLE
            }
        }
    }

    override fun getLayout() = R.layout.main_feed_section_header
}