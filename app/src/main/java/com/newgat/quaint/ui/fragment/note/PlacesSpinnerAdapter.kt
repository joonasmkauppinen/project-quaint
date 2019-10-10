package com.newgat.quaint.ui.fragment.note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.newgat.quaint.R

class PlacesSpinnerAdapter(
    val context: Context, private val placeNames: List<String>
) : BaseAdapter() {

    private val placeHolderString = context.resources.getString(R.string.hint_add_place)
    private val listItems = ArrayList<String>().apply {
        add(placeHolderString)
        placeNames.forEach {
            add(it)
        }
    }
    private val _inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = _inflater.inflate(R.layout.dropdown_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        if (position == 0) {
            val color = ContextCompat.getColor(context, R.color.colorInputHint)
            viewHolder.label.setTextColor(color)
        }

        viewHolder.label.text = listItems[position]

        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listItems.count()
    }

    private class ViewHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.dropDownTextView) as TextView
    }

}