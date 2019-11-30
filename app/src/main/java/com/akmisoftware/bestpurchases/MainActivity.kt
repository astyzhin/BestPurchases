package com.akmisoftware.bestpurchases

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

        fab.setOnClickListener { view ->
            Log.d("$TAG Fab", "Added placeholder Event")

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
                    groupAdapter.clear()
                    list.onEach {
                        Log.d("$TAG onStart", "$it")
                        groupAdapter.add(EventItem(it))
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
    }

    private fun handleError(error: Throwable) {
        Log.e("$TAG EventSub", error.localizedMessage as String)
    }

    private fun deleteAllUsers() {
        thread {
            viewModel.deleteAllEvents()
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
                deleteAllUsers()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
