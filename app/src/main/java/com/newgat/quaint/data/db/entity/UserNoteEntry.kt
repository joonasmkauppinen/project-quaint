package com.newgat.quaint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_notes")
data class UserNoteEntry(
    val title: String,
    val content: String,
    val note_location_id: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}