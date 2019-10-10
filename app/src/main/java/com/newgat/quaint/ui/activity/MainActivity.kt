package com.newgat.quaint.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.newgat.quaint.R
import com.newgat.quaint.internal.ActionType
import com.newgat.quaint.ui.fragment.bottomsheetmodal.BottomSheetModalFragment
import com.newgat.quaint.ui.fragment.locationssection.LocationsSectionFragment
import com.newgat.quaint.ui.fragment.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_ACTION_TYPE = "com.newgat.quaint.ACTION_TYPE"

class MainActivity : AppCompatActivity(), View.OnClickListener, BottomSheetModalFragment.BottomSheetListener {

    companion object {
        const val APP_PERMISSIONS_ACCESS_FINE_LOCATION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openBottomSheetButton.setOnClickListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.rootLayout, LocationsSectionFragment())
                .commit()
        }

        // TODO: Move logic into PermissionsProvider
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                APP_PERMISSIONS_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onClick(v: View?) {
        BottomSheetModalFragment().show(supportFragmentManager, "MainBottomSheetModal")
    }

    override fun onActionClicked(action: ActionType) {
        val intent = Intent(this, NewActionActivity::class.java).apply {
            putExtra(EXTRA_ACTION_TYPE, action)
        }
        startActivity(intent)

    }
}
