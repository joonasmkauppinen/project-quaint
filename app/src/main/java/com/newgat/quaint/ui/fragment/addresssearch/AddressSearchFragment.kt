package com.newgat.quaint.ui.fragment.addresssearch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.newgat.quaint.R
import com.newgat.quaint.databinding.AddressSearchFragmentBinding
import kotlinx.android.synthetic.main.address_search_fragment.view.*
import org.jetbrains.anko.support.v4.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AddressSearchFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: AddressSearchViewModelFactory by instance()

    private lateinit var binding: AddressSearchFragmentBinding
    private lateinit var viewModel: AddressSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.address_search_fragment,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddressSearchViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.addressInputEditText.doAfterTextChanged { viewModel.onEditTextChange() }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearPlaceSearchFromRepository()
    }

}
