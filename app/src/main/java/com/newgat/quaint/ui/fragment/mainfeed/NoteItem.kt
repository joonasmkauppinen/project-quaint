package com.newgat.quaint.ui.fragment.mainfeed

import com.newgat.quaint.R
import com.newgat.quaint.data.db.entity.UserNoteEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.main_feed_list_item.*

class NoteItem(
    private val userNoteEntry: UserNoteEntry
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            titleTextView.text = userNoteEntry.title
            bodyTextView.text = userNoteEntry.content
        }
    }

    override fun getLayout() = R.layout.main_feed_list_item
}