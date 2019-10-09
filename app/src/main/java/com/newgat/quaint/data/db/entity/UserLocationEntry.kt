package com.newgat.quaint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_locations")
data class UserLocationEntry (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val description: String
)