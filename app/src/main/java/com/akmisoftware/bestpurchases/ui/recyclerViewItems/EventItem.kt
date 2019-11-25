package com.akmisoftware.bestpurchases.ui.recyclerViewItems

import com.akmisoftware.bestpurchases.R
import com.akmisoftware.bestpurchases.model.Event
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_event.view.*

class EventItem(val event: Event) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            event_name.text = event.name
        }
    }

    override fun getLayout(): Int {
    return R.layout.item_event
    }
}