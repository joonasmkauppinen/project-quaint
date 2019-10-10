package com.newgat.quaint.data.geofencing

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.preference.PreferenceManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import java.util.ArrayList

class Geofencer {

    private enum class PendingGeofenceTask {
        ADD, REMOVE, NONE
    }

    private var mGeofencingClient: GeofencingClient? = null

    private var mGeofenceList: ArrayList<Geofence>? = null

    private var mGeofencePendingIntent: PendingIntent? = null

    private var mPendingGeofenceTask = PendingGeofenceTask.NONE

    private fun getGeofencingRequest(): GeofencingRequest {
        val builder = GeofencingRequest.Builder()

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)

        builder.addGeofences(mGeofenceList)

        return builder.build()
    }

    fun addGeofences() {
        if (!checkPermissions()) {
            mPendingGeofenceTask = PendingGeofenceTask.ADD
            return
        }
        if (!checkPermissions()) {
            return
        }

        mGeofencingClient?.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
            ?.addOnCompleteListener(this)
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent
        }
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        mGeofencePendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return mGeofencePendingIntent
    }

    private fun getGeofencesAdded(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
            GeofencingConstants.GEOFENCES_ADDED_KEY, false
        )
    }

    private fun updateGeofencesAdded(added: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putBoolean(GeofencingConstants.GEOFENCES_ADDED_KEY, added)
            .apply()
    }

    private fun performPendingGeofenceTask() {
        if (mPendingGeofenceTask == PendingGeofenceTask.ADD) {
            addGeofences()
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

}