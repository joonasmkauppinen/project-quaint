package com.newgat.quaint.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.newgat.quaint.R
import com.newgat.quaint.internal.ActionType
import kotlinx.android.synthetic.main.activity_new_action.*

class NewActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_action)

        val action = intent.getSerializableExtra(EXTRA_ACTION_TYPE) as ActionType
        setToolbarTitle(action)
    }

    private fun setToolbarTitle(action: ActionType) {
        val title = when (action) {
            ActionType.NOTE -> getString(R.string.new_note_title)
            ActionType.LOCATION -> getString(R.string.new_location_title)
        }
        actionTitleTextView.text = title
    }

    fun onCloseClicked(v: View) {
        onBackPressed()
    }
}
