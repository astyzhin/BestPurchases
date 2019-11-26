package com.akmisoftware.bestpurchases.ui.recyclerViewItems

import com.akmisoftware.bestpurchases.R
import com.akmisoftware.bestpurchases.model.User
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_attendee.view.*

class UserItem(val user: User) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            attendee_imageView.setImageResource(user.image)
            attendee_name_textView.text = user.name
            attendee_phone_number.text = user.phoneNumber
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_attendee
    }
}