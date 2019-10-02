package com.newgat.quaint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntry (
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String
)