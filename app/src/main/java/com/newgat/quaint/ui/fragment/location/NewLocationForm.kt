package com.newgat.quaint.ui.fragment.location

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.newgat.quaint.R
import com.newgat.quaint.data.db.entity.Prediction
import com.newgat.quaint.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.new_location_form_fragment.view.*
import org.jetbrains.anko.textColor
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.ClassCastException

class NewLocationForm : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: NewLocationFormViewModelFactory by instance()

    private var listener: NewLocationFormListener? = null
    private lateinit var viewModel: NewLocationFormViewModel
    private lateinit var rootView: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewLocationFormListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement NewLocationFormListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.new_location_form_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewLocationFormViewModel::class.java)

        bindUI()
    }

    private fun bindUI() {
        viewModel.userSelectedAddress.observe(this@NewLocationForm, Observer { prediction ->
            if (prediction != null)
                updateSelectedAddress(prediction)
            else
                setToAddressPlaceholder()
        })
        rootView.addressListItem.setOnClickListener { listener!!.openAddressSearch() }
        rootView.placeNameEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.updatePlaceName(text.toString())
        }
    }

    private fun setToAddressPlaceholder() {
        rootView.newLocationAddressTextView.apply {
            text = getString(R.string.input_hint_address)
            textColor = ContextCompat.getColor(context, R.color.colorInputHint)
        }
    }

    private fun updateSelectedAddress(prediction: Prediction) {
        rootView.newLocationAddressTextView.apply {
            text = prediction.structured_formatting.main_text
            textColor = ContextCompat.getColor(context, R.color.colorWhite)
        }
    }

    interface NewLocationFormListener {
        fun openAddressSearch()
    }

}
