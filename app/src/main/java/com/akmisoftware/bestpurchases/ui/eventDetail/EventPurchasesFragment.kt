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
import com.akmisoftware.bestpurchases.model.Purchase
import com.akmisoftware.bestpurchases.ui.recyclerViewItems.PurchaseItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_event_purchases.view.*

class EventPurchasesFragment : Fragment() {

    companion object {
        private val TAG = EventPurchasesFragment::class.java.simpleName
    }

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_event_purchases, container, false)
        root.fragment_purchases_recyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@EventPurchasesFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = groupAdapter
            groupAdapter.clear()
        }
        getPurchases()
        return root
    }

    private fun getPurchases() {
        val eventObservable = Observable
            .fromIterable(DataSource.createPurchasesDB())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError)
        disposable.add(eventObservable)
    }

    private fun handleResponse(purchase: Purchase) {
        Log.d("$TAG PurchaseSub", "onNext: ${purchase.name}")
        groupAdapter.add(PurchaseItem(purchase))
    }

    private fun handleError(error: Throwable) {
        Log.e("$TAG PurchaseSub", error.localizedMessage as String)
    }

    override fun onStop() {
        super.onStop()
        if (!disposable.isDisposed) {
            disposable.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}
