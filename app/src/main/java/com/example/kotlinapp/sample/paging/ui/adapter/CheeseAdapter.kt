package com.example.kotlinapp.sample.paging.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ListItemCheeseBinding
import com.example.kotlinapp.sample.paging.data.CheeseListItem
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.views.DataBoundListAdapter

class CheeseAdapter(
    appExecutors: AppExecutors
) : DataBoundListAdapter<CheeseListItem, ListItemCheeseBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<CheeseListItem>() {
        override fun areItemsTheSame(oldItem: CheeseListItem, newItem: CheeseListItem): Boolean {
            return if (oldItem is CheeseListItem.ListItem && newItem is CheeseListItem.ListItem) {
                oldItem.cheese.id == newItem.cheese.id
            } else if (oldItem is CheeseListItem.Seprator && newItem is CheeseListItem.Seprator) {
                oldItem.name == newItem.name
            } else {
                oldItem == newItem
            }
        }

        override fun areContentsTheSame(oldItem: CheeseListItem, newItem: CheeseListItem): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ListItemCheeseBinding {
       return DataBindingUtil.inflate(
           LayoutInflater.from(parent.context),
           R.layout.list_item_cheese,
           parent,
           false
       )
    }

    override fun bind(binding: ListItemCheeseBinding, item: CheeseListItem) {
        binding.cheeseItem = item
    }
}