package com.newgat.quaint.ui.fragment.addresssearch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.newgat.quaint.R
import com.newgat.quaint.data.db.entity.Prediction
import com.newgat.quaint.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.address_prediction_list_item.view.*
import kotlinx.android.synthetic.main.address_predictions_footer.*
import kotlinx.android.synthetic.main.address_search_fragment.*
import kotlinx.android.synthetic.main.address_search_fragment.view.*
import org.jetbrains.anko.support.v4.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AddressSearchFragment : ScopedFragment(), KodeinAware, View.OnClickListener, AddressPredictionItem.PredictionItemClickListener {

    override val kodein by closestKodein()
    private val viewModelFactory: AddressSearchViewModelFactory by instance()

    private lateinit var viewModel: AddressSearchViewModel
    private lateinit var rootView: View
    private lateinit var addressPredictionsSection: Section

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
        initRecyclerView()
        viewModel.addressPredictions.observe(this@AddressSearchFragment, Observer { predictions ->
            if (predictions == null) return@Observer
            updateRecyclerViewItems(predictions.toAddressPredictionItems())
        })
        
        rootView.clearAddressSearchButton.setOnClickListener(this)
        rootView.addressInputEditText.doOnTextChanged { text, _, _, _ ->
            updateClearButton(text!!)
            viewModel.onEditTextChange(text.toString())
        }
        updateClearButton("")
    }

    private fun List<Prediction>.toAddressPredictionItems() : List<AddressPredictionItem> {
        return this.map {
            AddressPredictionItem(this@AddressSearchFragment, it)
        }
    }

    private fun initRecyclerView() {
        addressPredictionsSection= Section().apply {
            setFooter(AddressPredictionFooter())
        }
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            add(addressPredictionsSection)
            setOnItemClickListener { item, view ->
                when (view) {
                    chooseOnMapItem -> toast("TODO: open map")
                }
            }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AddressSearchFragment.context)
            adapter = groupAdapter
        }
    }

    private fun updateRecyclerViewItems(items: List<AddressPredictionItem>) {
        addressPredictionsSection.update(items)
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO: implement search clean up
    }

    override fun onFillClicked(streetName: String) {
        rootView.addressInputEditText.setText(streetName)
    }

    override fun onItemPredictionClicked() {
        toast("Clicked on prediction item")
    }

    override fun onClick(v: View?) {
        when (v) {
            closeAddressSearchButton -> activity!!.onBackPressed()
            clearAddressSearchButton -> clearSearchField()
        }
    }

    private fun clearSearchField() {
        rootView.addressInputEditText.text.clear()
    }

    private fun disableButton() {
        rootView.clearAddressSearchButton.apply {
            isEnabled = false
            visibility = View.INVISIBLE
        }
    }

    private fun enableButton() {
        rootView.clearAddressSearchButton.apply {
            isEnabled = true
            visibility = View.VISIBLE
        }
    }

    private fun updateClearButton(text: CharSequence) {
        val value = text.toString()
        if (value != "") enableButton() else disableButton()
    }
}
