package br.com.tosin.newconceptsandroid.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_fake.view.*

class FakeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val image = itemView.imageView_itemFake!!
    val title = itemView.textView_itemFake_title!!
    val msg = itemView.textView_itemFake_message!!
}