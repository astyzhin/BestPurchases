package com.akmisoftware.bestpurchases

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.akmisoftware.bestpurchases.db.AppConstants
import com.akmisoftware.bestpurchases.model.Event
import com.akmisoftware.bestpurchases.ui.eventDetail.EventDetailPagerAdapter
import kotlinx.android.synthetic.main.activity_event_detail.*
import java.text.SimpleDateFormat

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
        setupView()

        val fragmentAdapter = EventDetailPagerAdapter(supportFragmentManager)
        event_viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(event_viewPager)
    }

    private fun setupView() {
        val dateFormatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)
        if (event != null) {

            event_detail_image.setImageResource(event!!.image)
            event_date_time_value.text = dateFormatter.format(event?.date) + " " + event?.time
            event_attendees_value.text = event?.attendees.toString()

        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_event_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_event_about -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
