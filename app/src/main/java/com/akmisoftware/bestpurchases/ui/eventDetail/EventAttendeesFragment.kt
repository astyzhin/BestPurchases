package com.akmisoftware.bestpurchases.ui.eventDetail


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmisoftware.bestpurchases.R
import com.akmisoftware.bestpurchases.db.DataSource
import com.akmisoftware.bestpurchases.model.User
import com.akmisoftware.bestpurchases.ui.recyclerViewItems.UserItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_event_attendees.view.*

class EventAttendeesFragment : Fragment() {

    companion object {
        private val TAG = EventAttendeesFragment::class.java.simpleName
    }

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_event_attendees, container, false)
        root.fragment_attendees_recyclerView.apply {
            layoutManager = LinearLayoutManager(this@EventAttendeesFragment.context)
            adapter = groupAdapter
        }
        getUsers()
        return root
    }

    private fun getUsers() {
        val eventObservable = Observable
            .fromIterable(DataSource.createUsersBD())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(user: User) {
        Log.d("$TAG UserSub", "onNext: ${user.name}")
        groupAdapter.add(UserItem(user))
    }
    private fun handleError(error: Throwable) {
        Log.e("$TAG UserSub", error.localizedMessage as String)
    }


}
