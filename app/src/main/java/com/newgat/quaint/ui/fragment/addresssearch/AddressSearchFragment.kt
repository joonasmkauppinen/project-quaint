package com.newgat.quaint.ui.fragment.addresssearch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.newgat.quaint.R

class AddressSearchFragment : Fragment() {

    companion object {
        fun newInstance() = AddressSearchFragment()
    }

    private lateinit var viewModel: AddressSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.address_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddressSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
