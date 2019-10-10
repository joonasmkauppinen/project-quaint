package com.newgat.quaint.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.newgat.quaint.R
import com.newgat.quaint.internal.ActionType
import com.newgat.quaint.ui.fragment.addresssearch.AddressSearchFragment
import com.newgat.quaint.ui.fragment.location.NewLocationForm
import com.newgat.quaint.ui.fragment.note.NewNoteForm
import kotlinx.android.synthetic.main.activity_new_action.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class NewActionActivity : AppCompatActivity(), KodeinAware, NewLocationForm.NewLocationFormListener {

    override val kodein by closestKodein()
    private val viewModelFactory: NewActionViewModelFactory by instance()

    private lateinit var viewModel: NewActionViewModel
    private lateinit var action: ActionType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_action)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewActionViewModel::class.java)

        action = intent.getSerializableExtra(EXTRA_ACTION_TYPE) as ActionType
        setToolbarTitle(action)
        setActionForm(action)

        saveActionButton.setOnClickListener { saveNewAction() }
    }



    private fun setToolbarTitle(action: ActionType) {
        val title = when (action) {
            ActionType.NOTE -> getString(R.string.new_note_title)
            ActionType.LOCATION -> getString(R.string.new_location_title)
        }
        addressInputEditText.text = title
    }

    private fun saveNewAction() {
        when (action) {
            ActionType.NOTE -> viewModel.saveUserNote()
            ActionType.LOCATION -> viewModel.saveUserPlace()
        }
        finish()
    }

    private fun setActionForm(action: ActionType) {
        supportFragmentManager.beginTransaction().apply {
            when (action) {
                ActionType.NOTE -> replace(R.id.actionFormRoot, NewNoteForm())
                ActionType.LOCATION -> replace(R.id.actionFormRoot, NewLocationForm())
            }
            commit()
        }
    }

    fun onClosePressed(v: View) {
        onBackPressed()
    }

    override fun openAddressSearch() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_bottom_enter, R.anim.slide_bottom_exit, R.anim.slide_bottom_enter, R.anim.slide_bottom_exit)
            .add(R.id.newActionRoot, AddressSearchFragment())
            .addToBackStack(null)
            .commit()
    }
}
