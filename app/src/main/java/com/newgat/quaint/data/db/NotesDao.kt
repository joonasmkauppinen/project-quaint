package com.newgat.quaint.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newgat.quaint.data.db.entity.UserNoteEntry

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(note: UserNoteEntry)

    @Query("select * from  user_notes where user_notes.note_location_id = :locationId")
    fun getNotesForLocation(locationId: Int): LiveData<List<UserNoteEntry>>

    @Query("select * from user_notes")
    fun getAllNotes(): LiveData<List<UserNoteEntry>>
}