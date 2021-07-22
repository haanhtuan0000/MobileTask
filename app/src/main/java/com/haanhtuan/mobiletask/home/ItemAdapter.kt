package com.haanhtuan.mobiletask.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haanhtuan.mobiletask.data.database.Item
import com.haanhtuan.mobiletask.databinding.ItemLayoutBinding

class ItemAdapter(private var items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    private lateinit var itemBinding: ItemLayoutBinding
    private lateinit var layoutInflater: LayoutInflater

    fun update(currentList: List<Item>) {
        items = currentList
        notifyDataSetChanged()
    }

    fun getItemPosition(pos: Int): Item {
        return items[pos]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        layoutInflater = LayoutInflater.from(parent.context)

        itemBinding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
        return ItemHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ItemHolder(private val itemBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Item) {
            itemBinding.itemText.text = item.msg
        }

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position])
    }
}