package com.newgat.quaint.ui.fragment.addresssearch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.newgat.quaint.R
import com.newgat.quaint.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.address_search_fragment.*
import kotlinx.android.synthetic.main.address_search_fragment.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AddressSearchFragment : ScopedFragment(), KodeinAware, View.OnClickListener {

    override val kodein by closestKodein()
    private val viewModelFactory: AddressSearchViewModelFactory by instance()

    private lateinit var viewModel: AddressSearchViewModel
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.address_search_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddressSearchViewModel::class.java)
        bindUI()
    }

    private fun bindUI() {
        viewModel.addressPredictions.observe(this@AddressSearchFragment, Observer { predictions ->
            if (predictions.isEmpty() || predictions == null) return@Observer
            rootView.testText.text = predictions.toString()
        })

        rootView.closeAddressSearch.setOnClickListener(this)
        rootView.clearAddressSearchButton.setOnClickListener(this)
        rootView.addressInputEditText.doOnTextChanged { text, start, count, after ->
            Log.d("AddressSearchFragment", "Edit text input: $text")
            viewModel.onEditTextChange(text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO: implement search clean up
    }

    override fun onClick(v: View?) {
        when (v) {
            closeAddressSearch -> activity!!.onBackPressed()
            clearAddressSearchButton -> clearSearchField()
        }
    }

    private fun clearSearchField() {
        rootView.addressInputEditText.text.clear()
    }
}
