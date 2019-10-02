package com.newgat.quaint.ui.fragment.map

import android.content.Context
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.newgat.quaint.R
import kotlinx.android.synthetic.main.fragment_map.view.*


class MapFragment : Fragment(), OnMapReadyCallback, View.OnClickListener {

    private val TAG = "MapFragment"

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var  fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        view.closeMapBtn.setOnClickListener(this)
        return view
    }

    override fun onStart() {
        super.onStart()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context!!.applicationContext)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location ->
            val currentLocation = LatLng(location.latitude, location.longitude)
            try {
                 val success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(context,
                        R.raw.maps_style_json
                    )
                 )
                if (!success) Log.d(TAG, "Style parsing failed.")
            } catch (e: Resources.NotFoundException) {
                Log.d(TAG, "Can't find style. Error: $e")
            }

            map.addMarker(
                MarkerOptions()
                    .position(currentLocation)
                    .title("Your last known location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
        }
    }

    override fun onClick(v: View?) {
        listener?.onFragmentInteraction()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
    }

    companion object {
        fun newInstance() = MapFragment()
    }
}
