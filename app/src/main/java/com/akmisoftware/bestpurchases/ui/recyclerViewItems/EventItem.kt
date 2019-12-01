package com.akmisoftware.bestpurchases.ui.recyclerViewItems

import com.akmisoftware.bestpurchases.R
import com.akmisoftware.bestpurchases.model.Event
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_event.view.*
import java.text.SimpleDateFormat

class EventItem(val event: Event) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            event_image.setImageResource(event.image)
            event_name.text = event.name
            event_attendees_amount.text = event.attendeesAmount.toString() + " people"
            dateFormatter(viewHolder)
        }
    }


    private fun dateFormatter(viewHolder: GroupieViewHolder) {
        val dateFormatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)
//        val timeFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)
        viewHolder.itemView.apply {
            event_date.text = dateFormatter.format(event.date)
            event_time.text = event.time
        }
    }

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is EventItem)
            return false
        if (this.event != other.event)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? EventItem)
    }

    override fun hashCode(): Int {
        return event.hashCode()
    }

    override fun getLayout(): Int {
        return R.layout.item_event
    }
}