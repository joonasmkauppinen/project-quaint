package com.newgat.quaint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntry (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val description: String
)