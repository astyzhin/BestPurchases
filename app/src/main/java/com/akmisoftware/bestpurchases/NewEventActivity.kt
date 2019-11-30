package com.akmisoftware.bestpurchases

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.akmisoftware.bestpurchases.model.Event
import com.akmisoftware.bestpurchases.ui.EventViewModel
import com.akmisoftware.bestpurchases.ui.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.random.Random

class NewEventActivity : AppCompatActivity() {
    companion object {
        private val TAG = NewEventActivity::class.java.simpleName
    }

    //ViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: EventViewModel by viewModels { viewModelFactory }
    //RxJava object
    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)
        viewModelFactory = Injection.provideViewModelFactory(this)
        supportActionBar?.apply {
            title = "Create new event"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }
    }

    private fun addEvent() {
        disposable.add(viewModel.updateEvent(randomEvent())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("$TAG OptionsMenu", "Added placeholder Event$")
            })
    }

    private fun randomEvent(): Event {
        val randomNumber = Random.nextInt(0, 5)
        return when (randomNumber) {
            0 ->
                Event(
                    UUID.randomUUID().toString(),
                    "Birthday",
                    20,
                    Date(1574719770000),
                    "21:00",
                    R.drawable.birthday
                )

            1 ->
                Event(
                    UUID.randomUUID().toString(),
                    "Party",
                    10,
                    Date(1574892246000),
                    "11:00",
                    R.drawable.party
                )

            2 ->
                Event(
                    UUID.randomUUID().toString(),
                    "Test Event",
                    5,
                    Date(),
                    "10:00",
                    R.drawable.box
                )

            3 ->
                Event(
                    UUID.randomUUID().toString(),
                    "Celebration",
                    32,
                    Date(1577482460000),
                    "20:00",
                    R.drawable.celebration
                )
            else ->
                Event(
                    UUID.randomUUID().toString(),
                    "Else Case",
                    10,
                    Date(1574892246000),
                    "15:51",
                    R.drawable.box
                )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_event, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_create_event -> {
                Toast.makeText(this, "Event created", Toast.LENGTH_LONG).show()
                addEvent()
                finish()
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
                true
            }
            android.R.id.home -> {
                finish()
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
