package com.example.kotlinapp.sample.paging.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.kotlinapp.R
import com.example.kotlinapp.sample.paging.data.CheeseListItem

class CheeseAdapter : PagingDataAdapter<CheeseListItem, CheeseViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheeseViewHolder {
        return CheeseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_cheese,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CheeseListItem>() {
            override fun areItemsTheSame(
                oldItem: CheeseListItem,
                newItem: CheeseListItem
            ): Boolean {
                return if (oldItem is CheeseListItem.ListItem && newItem is CheeseListItem.ListItem) {
                    oldItem.cheese.id == newItem.cheese.id
                } else if (oldItem is CheeseListItem.Seprator && newItem is CheeseListItem.Seprator) {
                    oldItem.name == newItem.name
                } else {
                    oldItem == newItem
                }
            }

            override fun areContentsTheSame(
                oldItem: CheeseListItem,
                newItem: CheeseListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}