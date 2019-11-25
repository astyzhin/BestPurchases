package com.akmisoftware.bestpurchases.db

import com.akmisoftware.bestpurchases.model.Event
import com.akmisoftware.bestpurchases.model.Purchase
import com.akmisoftware.bestpurchases.model.User

object DataSource {

    fun createEventsDB(): ArrayList<Event> {
    val events = ArrayList<Event>()
        events.apply {
            add(Event("Birthday"))
            add(Event("Party"))
            add(Event("Celebration"))
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