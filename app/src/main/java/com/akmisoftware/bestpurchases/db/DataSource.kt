package com.akmisoftware.bestpurchases.db

import com.akmisoftware.bestpurchases.R
import com.akmisoftware.bestpurchases.model.Event
import com.akmisoftware.bestpurchases.model.Purchase
import com.akmisoftware.bestpurchases.model.User
import java.util.*
import kotlin.collections.ArrayList

object DataSource {

    fun createEventsDB(): ArrayList<Event> {
    val events = ArrayList<Event>()
        events.apply {
            add(Event("Birthday", 20, Date(1574719770000), "21:00", R.drawable.birthday))
            add(Event("Party", 10, Date(1574892246000), "11:00", R.drawable.party))
            add(Event("Celebration", 32, Date(1577482460000), "20:00", R.drawable.celebration))
            return events
        }
    }
    fun createUsersBD(): ArrayList<User> {
        val users = ArrayList<User>()
        users.apply {
            add(User("Andy"))
            add(User("Tom"))
            add(User("Jim"))
            return users
        }
    }
    fun createPurchasesDB(): ArrayList<Purchase> {
        val purchases = ArrayList<Purchase>()
        purchases.apply {
            add(Purchase("Toy"))
            add(Purchase("Box"))
            add(Purchase("Spinner"))
            return purchases
        }
    }
}