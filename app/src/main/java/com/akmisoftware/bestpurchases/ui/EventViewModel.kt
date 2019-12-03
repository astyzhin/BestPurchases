package com.akmisoftware.bestpurchases.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.akmisoftware.bestpurchases.db.EventDao
import com.akmisoftware.bestpurchases.model.Event
import io.reactivex.Completable
import io.reactivex.Flowable

class EventViewModel(private val dataSource: EventDao) : ViewModel() {

    fun loadAllEvents(): Flowable<List<Event>> {
        dataSource.loadAllEvents().apply {
            Log.d("DBfromVM", "$this")
        }
        return dataSource.loadAllEvents()
    }

    fun loadEventByID(id: String): Flowable<Event> {
        Log.d("DB","${dataSource}")
        return dataSource.getEventById(id)
//            .map { event -> event }
    }

    fun updateEvent(event: Event): Completable {
        val eventForUpdate = Event(event.id, event.name, event.attendeesAmount, event.date, event.time, event.image)
        return dataSource.insertEvent(eventForUpdate)
    }

    fun deleteEvent(event: Event) {
        return dataSource.deleteEvent(event.id)
    }
    fun deleteAllEvents() {
        return dataSource.deleteAllEvents()
    }

    companion object {
        // using a hardcoded value for simplicity
        const val EVENT_ID = "1"
    }
}