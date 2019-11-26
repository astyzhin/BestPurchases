package com.akmisoftware.bestpurchases

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmisoftware.bestpurchases.db.AppConstants
import com.akmisoftware.bestpurchases.db.DataSource
import com.akmisoftware.bestpurchases.model.Event
import com.akmisoftware.bestpurchases.ui.recyclerViewItems.EventItem
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var eventsSection: Section
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initRecyclerView()
        getEvents()



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(home_recyclerView.context, linearLayoutManager.orientation)

        home_recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = groupAdapter
            addItemDecoration(decoration)
        }
    }

    private fun getEvents() {
        val eventObservable = Observable
            .fromIterable(DataSource.createEventsDB())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(event: Event) {
        Log.d("EventSub", "onNext: ${event.name}")
        groupAdapter.add(EventItem(event))
        groupAdapter.setOnItemClickListener { item, view ->
            val intent = Intent(view.context,EventDetailActivity::class.java)
            val eventItem = item as EventItem
            intent.putExtra(AppConstants.EVENT_INFO, eventItem.event)
            startActivity(intent)
        }
    }
    private fun handleError(error: Throwable) {
        Log.e("EventSub", error.localizedMessage as String)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
