package com.newgat.quaint.ui.fragment.addresssearch

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.newgat.quaint.R
import kotlinx.android.synthetic.main.address_search_fragment.view.*

class AddressSearchFragment : Fragment() {

    private lateinit var listener: AddressSearchListener
    private lateinit var viewModel: AddressSearchViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as AddressSearchListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement AddressSearchListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.address_search_fragment, container, false)
        view.closeAddressSearch.setOnClickListener{ listener.closeAddressSearch() }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddressSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    interface AddressSearchListener {
        fun closeAddressSearch()
    }

}
