package br.com.tosin.newconceptsandroid.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.entity.FakeData
import br.com.tosin.newconceptsandroid.ui.main.adapter.FakeViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

typealias onItemClicked = (item: FakeData, position: Int) -> Unit

class FakeAdapter(val listener: onItemClicked): RecyclerView.Adapter<FakeViewHolder>() {

    private var list = mutableListOf<FakeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FakeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_fake, parent, false)
        return FakeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FakeViewHolder, position: Int) {
        val fake = list[position]

        val fullName = fake.name.first + " " +  fake.name.last
        holder.title.text = fullName
        holder.msg.text = fake.about

        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_person_gray_24dp)
            .error(R.drawable.ic_error_outline_red_24dp)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

        Glide.with(holder.itemView.context)
                .load(fake.picture)
                .apply(options)
                .into(holder.image)

        holder.itemView.setOnClickListener {
            listener(fake, position)
        }
    }

    fun setList(list: List<FakeData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun getItemList(index: Int): FakeData? {
        return if (index in 0..list.size) {
            list[index]
        }
        else {
            null
        }
    }

    fun removeItemByPosition(position: Int) {
        this.list.removeAt(position)
        notifyItemChanged(position)
    }
}

