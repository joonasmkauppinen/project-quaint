package com.newgat.quaint.ui.fragment.mainfeed

import com.newgat.quaint.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.main_feed_section_header.*

class SectionHeader(
    private val headerName: String
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            sectionHeader.text = headerName
        }
    }

    override fun getLayout() = R.layout.main_feed_section_header
}