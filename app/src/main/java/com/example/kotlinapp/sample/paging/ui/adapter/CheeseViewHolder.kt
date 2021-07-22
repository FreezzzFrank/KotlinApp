package com.example.kotlinapp.sample.paging.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.databinding.ListItemCheeseBinding
import com.example.kotlinapp.sample.paging.data.Cheese
import com.example.kotlinapp.sample.paging.data.CheeseListItem

class CheeseViewHolder constructor(val binding: ListItemCheeseBinding) : RecyclerView.ViewHolder(binding.root) {
    var cheese: Cheese? = null

    fun bind(item: CheeseListItem?) {
        binding.cheeseItem = item
        cheese = (item as? CheeseListItem.ListItem)?.cheese
    }
}