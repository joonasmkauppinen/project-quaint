package com.newgat.quaint.data.db.entity


import com.google.gson.annotations.SerializedName

data class Geometry(
    val location: Location,
    @SerializedName("location_type")
    val locationType: String,
    val viewport: Viewport
)