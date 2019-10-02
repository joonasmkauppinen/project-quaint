package com.newgat.quaint.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newgat.quaint.data.db.entity.LocationEntry

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationEntry: LocationEntry)

    @Query("select * from locations")
    fun getAllLocations(): LiveData<List<LocationEntry>>
}