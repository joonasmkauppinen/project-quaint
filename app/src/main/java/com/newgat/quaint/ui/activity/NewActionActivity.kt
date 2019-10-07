package com.newgat.quaint.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.newgat.quaint.R
import com.newgat.quaint.internal.ActionType
import com.newgat.quaint.ui.fragment.addresssearch.AddressSearchFragment
import com.newgat.quaint.ui.fragment.location.NewLocationForm
import kotlinx.android.synthetic.main.activity_new_action.*

class NewActionActivity : AppCompatActivity(), NewLocationForm.NewLocationFormListener, AddressSearchFragment.AddressSearchListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_action)

        val action = intent.getSerializableExtra(EXTRA_ACTION_TYPE) as ActionType
        setToolbarTitle(action)
        setActionForm(action)
    }

    private fun setToolbarTitle(action: ActionType) {
        val title = when (action) {
            ActionType.NOTE -> getString(R.string.new_note_title)
            ActionType.LOCATION -> getString(R.string.new_location_title)
        }
        addressInputEditText.text = title
    }

    private fun setActionForm(action: ActionType) {
        supportFragmentManager.beginTransaction().apply {
            when (action) {
//                ActionType.NOTE -> replace(R.id.actionForm,)
                ActionType.LOCATION -> replace(R.id.actionFormRoot, NewLocationForm())
            }
            commit()
        }
    }

    fun onCloseClicked(v: View) {
        onBackPressed()
    }

    override fun openAddressSearch() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_bottom_enter, R.anim.slide_bottom_exit, R.anim.slide_bottom_enter, R.anim.slide_bottom_exit)
            .add(R.id.newActionRoot, AddressSearchFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun closeAddressSearch() {
        onBackPressed()
    }
}
