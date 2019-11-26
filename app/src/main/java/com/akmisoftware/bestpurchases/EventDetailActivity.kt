package com.akmisoftware.bestpurchases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akmisoftware.bestpurchases.db.AppConstants
import com.akmisoftware.bestpurchases.model.Event

class EventDetailActivity : AppCompatActivity() {

    var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        event = intent.getParcelableExtra(AppConstants.EVENT_INFO)
        supportActionBar?.apply {
            title = event?.name
            setDisplayHomeAsUpEnabled(true)
        }

    }
}
