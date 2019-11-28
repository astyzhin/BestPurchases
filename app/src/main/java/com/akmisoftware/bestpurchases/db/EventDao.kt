package com.akmisoftware.bestpurchases.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akmisoftware.bestpurchases.model.Event
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun loadAllUsers(): Flowable<List<Event>>

    @Query("SELECT * FROM events WHERE eventId = :id")
    fun getUserById(id: String): Flowable<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: Event): Completable

    @Query("DELETE FROM events WHERE eventId = :id")
    fun deleteEvent(id: String)

    @Query("DELETE FROM events")
    fun deleteAllEvents()
}