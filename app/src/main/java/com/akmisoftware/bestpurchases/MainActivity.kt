package com.akmisoftware.bestpurchases

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmisoftware.bestpurchases.db.AppConstants
import com.akmisoftware.bestpurchases.model.Event
import com.akmisoftware.bestpurchases.ui.EventViewModel
import com.akmisoftware.bestpurchases.ui.ViewModelFactory
import com.akmisoftware.bestpurchases.ui.recyclerViewItems.EventItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    //Groupie
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    //ViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: EventViewModel by viewModels { viewModelFactory }
    //RxJava object
    private var disposable = CompositeDisposable()
    //State booleans
    private var isEventDetailsShown = false
    private var isEditEventShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModelFactory = Injection.provideViewModelFactory(this)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        }

        initRecyclerView()

        fab.setOnClickListener {
            Log.d("$TAG Fab", "Added placeholder Event")
            val intent = Intent(this, NewEventActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }
    }

    override fun onStart() {
        super.onStart()
        groupAdapter.clear()
        disposable.add(
            viewModel.loadAllEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    Log.d("$TAG onStart", list.count().toString())
                    groupAdapter.apply {
                        clear()
                        addAll(list.sortedWith(compareBy { it.date }).toEventItem())
                    }
                }, this::handleError)
        )
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        val decoration =
            DividerItemDecoration(
                home_recyclerView.context,
                linearLayoutManager.orientation
            )

        home_recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = groupAdapter
            addItemDecoration(decoration)
        }
        groupAdapter.apply {
            setOnItemClickListener { item, _ ->
                if (item is EventItem) {
                    if (!isEventDetailsShown) {
                        val intent = Intent(this@MainActivity, EventDetailActivity::class.java)
                        intent.putExtra(AppConstants.EVENT_INFO, item.event)
                        startActivity(intent)
                        isEventDetailsShown = true
                    }
                }
            }
            setOnItemLongClickListener { item, view ->
                if (item is EventItem) {
                    Log.d("$TAG Adapter", "Item long tapped")
                    val popupMenu = PopupMenu(this@MainActivity, view, Gravity.RIGHT)
                    popupMenu.apply {
                        inflate(R.menu.menu_popup)
                        try {
                            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                            fieldMPopup.isAccessible = true
                            val mPopup = fieldMPopup.get(popupMenu)
                            mPopup.javaClass
                                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                                .invoke(mPopup, true)
                        } catch (e: Exception) {
                            Log.e(
                                "$this",
                                "Could not show icon in popUp menu: ${e.localizedMessage}"
                            )
                        } finally {
                            show()
                            setOnMenuItemClickListener { menuItem ->
                                when (menuItem.itemId) {
                                    R.id.menu_popup_edit -> {
                                        Log.d("Popup", "Edit was tapped")
                                        //TODO: Edit menu item action.
                                        if (!isEditEventShown) {
                                            val intent = Intent(this@MainActivity, NewEventActivity::class.java)
                                            intent.putExtra(AppConstants.EVENT_INFO, item.event)
                                            startActivity(intent)
                                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
                                            isEditEventShown = true
                                        }
                                        true
                                    }
                                    R.id.menu_popup_delete -> {
                                        Log.d("Popup", "Delete was tapped")
                                        deleteEventFromDB(item.event)
                                        true
                                    }
                                    else -> false
                                }
                            }
                        }
                    }
                }
                return@setOnItemLongClickListener false
            }
        }
    }

    private fun handleError(error: Throwable) {
        Log.e("$TAG EventSub", error.localizedMessage as String)
    }

    private fun flushDataBase() {
        thread {
            viewModel.deleteAllEvents()
        }
    }

    private fun deleteEventFromDB(event: Event) {
        thread {
            viewModel.deleteEvent(event)
        }
    }

    private fun List<Event>.toEventItem(): List<EventItem>{
        return this.map { event ->
            EventItem(event)
        }
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
            R.id.action_settings -> {
                Toast.makeText(this, "DataBase cleared..", Toast.LENGTH_LONG).show()
                flushDataBase()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        isEventDetailsShown = false
        isEditEventShown = false
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
