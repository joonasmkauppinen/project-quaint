package com.newgat.quaint.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_locations")
data class UserLocationEntry (
    val name: String,
    @Embedded(prefix = "address_")
    val address: Prediction,
    @Embedded(prefix = "address_")
    val coordinates: Location
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}