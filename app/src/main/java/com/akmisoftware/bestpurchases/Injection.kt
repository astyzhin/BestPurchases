package com.akmisoftware.bestpurchases

import android.content.Context
import com.akmisoftware.bestpurchases.db.EventDao
import com.akmisoftware.bestpurchases.db.EventsDatabase
import com.akmisoftware.bestpurchases.ui.ViewModelFactory

object Injection {

    fun provideEventDataSource(context: Context): EventDao {
        val database = EventsDatabase.getInstance(context)
        return database.eventDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideEventDataSource(context)
        return ViewModelFactory(dataSource)
    }
}