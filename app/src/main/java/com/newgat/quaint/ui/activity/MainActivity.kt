package com.newgat.quaint.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.newgat.quaint.R
import com.newgat.quaint.ui.fragment.map.MapFragment

class MainActivity : AppCompatActivity(),
    MapFragment.OnFragmentInteractionListener {

    companion object {
        const val APP_PERMISSIONS_ACCESS_FINE_LOCATION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                APP_PERMISSIONS_ACCESS_FINE_LOCATION
            )
        }
    }

    fun showMap(v: View) {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .add(
                R.id.rootLayout,
                MapFragment.newInstance(), "MAP_FRAGMENT")
            .commit()
    }

    override fun onFragmentInteraction() {
        onBackPressed()
    }
}
