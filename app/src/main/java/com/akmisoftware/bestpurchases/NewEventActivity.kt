package com.akmisoftware.bestpurchases

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.akmisoftware.bestpurchases.model.Event
import com.akmisoftware.bestpurchases.ui.EventViewModel
import com.akmisoftware.bestpurchases.ui.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_event.*
import java.text.SimpleDateFormat
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
    //Date&Time formatter
    private val now = Date()
    private var eventDate = Date()
    private var eventTime =  Date()
    private val calendar = Calendar.getInstance()
    private val calDate = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        edit_eventDate_field.apply {
            setText(dateFormatter.format(calendar.time))
        }
        eventDate = calendar.time

    }
    private val calTme = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        edit_eventTime_field.apply {
            setText(timeFormatter.format(calendar.time))
        }
        eventTime = calendar.time
    }

    private val dateFormatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
    private val timeFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT, Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)
        viewModelFactory = Injection.provideViewModelFactory(this)
        supportActionBar?.apply {
            title = "Create new event"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }
        if (edit_eventName_field.text.isNullOrEmpty()) {
            edit_eventDate_field.setText(dateFormatter.format(Date()))
        }
        if (edit_eventTime_field.text.isNullOrEmpty()) {
            edit_eventTime_field.setText(timeFormatter.format(Date()))
        }

        edit_eventDate_field.setOnClickListener{
            DatePickerDialog(this@NewEventActivity, calDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        edit_eventTime_field.setOnClickListener {
            TimePickerDialog(this@NewEventActivity, calTme,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show()
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
        val name = edit_eventName_field.text?.toString() ?: "(No name)"
        val date = eventDate
        val time = eventTime
        return when (randomNumber) {
            0 ->
                Event(
                    UUID.randomUUID().toString(),
                    name,
                    20,
                    date,
                    time,
                    R.drawable.birthday
                )

            1 ->
                Event(
                    UUID.randomUUID().toString(),
                    name,
                    10,
                    date,
                    time,
                    R.drawable.party
                )

            2 ->
                Event(
                    UUID.randomUUID().toString(),
                    name,
                    5,
                    date,
                    time,
                    R.drawable.box
                )

            3 ->
                Event(
                    UUID.randomUUID().toString(),
                    name,
                    32,
                    date,
                    time,
                    R.drawable.celebration
                )
            else ->
                Event(
                    UUID.randomUUID().toString(),
                    name,
                    1,
                    date,
                    time,
                    R.drawable.doctor
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
                if (!edit_eventName_field.text.isNullOrEmpty()) {
                    Toast.makeText(this, "Event created", Toast.LENGTH_LONG).show()
                    addEvent()
                    finish()
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
                } else {
                    Toast.makeText(this, "Name required", Toast.LENGTH_LONG).show()
                    edit_eventName.error = "Name required"
                    edit_eventName_field.apply {
                        error = null
                        showKeyboard()
                    }
                }
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
fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
