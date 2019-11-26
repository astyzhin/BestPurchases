package com.akmisoftware.bestpurchases.ui.recyclerViewItems

import com.akmisoftware.bestpurchases.R
import com.akmisoftware.bestpurchases.model.Purchase
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_purchase.view.*

class PurchaseItem(val purchase: Purchase) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            purchase_image.setImageResource(purchase.image)
            purchase_name.text = purchase.name
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_purchase
    }

}