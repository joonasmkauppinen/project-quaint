package com.newgat.quaint.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newgat.quaint.data.db.entity.UserLocationEntry

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userLocationEntry: UserLocationEntry)

    @Query("select * from user_locations")
    fun getAllLocations(): LiveData<List<UserLocationEntry>>
}