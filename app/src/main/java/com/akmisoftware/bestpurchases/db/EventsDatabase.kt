package com.akmisoftware.bestpurchases.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akmisoftware.bestpurchases.model.Event

@Database(entities = [Event::class], version = 1)
@TypeConverters(DateTypeConverter::class, StringListConverter::class)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {

        @Volatile
        private var instance: EventsDatabase? = null

        fun getInstance(context: Context): EventsDatabase =
            instance ?: synchronized(this) {
                Log.d("DB", "$instance?")
                instance ?: buildDatabase(context).also { instance = it }

            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EventsDatabase::class.java, "Events.db")
                .build()
    }
}