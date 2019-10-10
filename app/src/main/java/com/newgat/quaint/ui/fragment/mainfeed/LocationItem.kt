package com.newgat.quaint.ui.fragment.mainfeed

import com.newgat.quaint.R
import com.newgat.quaint.data.db.entity.UserLocationEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.main_feed_list_item.*


class LocationItem(
    private val userLocationEntry: UserLocationEntry
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            titleTextView.text = userLocationEntry.name
            bodyTextView.text = userLocationEntry.address.description
        }
    }

    override fun getLayout() = R.layout.main_feed_list_item
}