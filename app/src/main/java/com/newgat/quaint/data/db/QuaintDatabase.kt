package com.newgat.quaint.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.newgat.quaint.data.db.entity.LocationEntry

@Database(
    entities = [LocationEntry::class],
    version = 1
)
abstract class QuaintDatabase : RoomDatabase() {
    abstract fun locationsDao(): LocationsDao

    // Create database singleton
    companion object {
        // @Volatile inorder for all threads to have immediate access to property
        @Volatile private var instance: QuaintDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                QuaintDatabase::class.java, "quaint.db")
                .build()
    }
}
