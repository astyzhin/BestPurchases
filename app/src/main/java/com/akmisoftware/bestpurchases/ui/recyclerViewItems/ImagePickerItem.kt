package com.akmisoftware.bestpurchases.ui.recyclerViewItems

import android.util.Log
import com.akmisoftware.bestpurchases.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_image_picker.view.*

class ImagePickerItem(val image: Int) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            image_event.setImageResource(image)
            Log.d("IPIA", "$image")
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_image_picker
    }
}