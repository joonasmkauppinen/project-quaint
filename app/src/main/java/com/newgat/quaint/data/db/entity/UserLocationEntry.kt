package com.newgat.quaint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_locations")
data class UserLocationEntry (
    val name: String,
    val description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}