package com.akmisoftware.bestpurchases.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akmisoftware.bestpurchases.db.EventDao

class ViewModelFactory(private val dataSource: EventDao) : ViewModelProvider.Factory {
    companion object {
        private val TAG = ViewModelFactory::class.java.simpleName
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(dataSource) as T
        }
        throw IllegalArgumentException("$TAG Unknown ViewModel class")
    }
}