package com.akmisoftware.bestpurchases.db

import com.akmisoftware.bestpurchases.R
import com.akmisoftware.bestpurchases.model.Event
import com.akmisoftware.bestpurchases.model.Purchase
import com.akmisoftware.bestpurchases.model.User
import java.util.*
import kotlin.collections.ArrayList

object DataSource {
    val events = ArrayList<Event>()

    fun createEventsDB(): ArrayList<Event> {
    val events = ArrayList<Event>()
        events.apply {
            add(Event(UUID.randomUUID().toString(), "Birthday", 20, Date(1574719770000), "21:00", R.drawable.birthday))
            add(Event(UUID.randomUUID().toString(), "Party", 10, Date(1574892246000), "11:00", R.drawable.party))
            add(Event(UUID.randomUUID().toString(), "Celebration", 32, Date(1577482460000), "20:00", R.drawable.celebration))
            return events
        }
    }
    fun populateEventsDB(): ArrayList<Event> {
        events.apply {
            add(Event(UUID.randomUUID().toString(), "Birthday", 20, Date(1574719770000), "21:00", R.drawable.birthday))
            add(Event(UUID.randomUUID().toString(), "Party", 10, Date(1574892246000), "11:00", R.drawable.party))
            add(Event(UUID.randomUUID().toString(), "Celebration", 32, Date(1577482460000), "20:00", R.drawable.celebration))
            return events
        }
    }
    fun addPlaceholderEvent(event: Event): ArrayList<Event> {
        events.add(event)
        return events
    }
    fun createUsersBD(): ArrayList<User> {
        val users = ArrayList<User>()
        users.apply {
            add(User("Andy", "+7(999)999-99-99", R.drawable.learning))
            add(User("Tom", "+7(555)555-55-55", R.drawable.doctor))
            add(User("Jim", "+7(777)777-77-77", R.drawable.student))
            return users
        }
    }
    fun createPurchasesDB(): ArrayList<Purchase> {
        val purchases = ArrayList<Purchase>()
        purchases.apply {
            add(Purchase("Toy", R.drawable.train))
            add(Purchase("Box", R.drawable.box))
            add(Purchase("Spinner", R.drawable.spinner))
            return purchases
        }
    }
}