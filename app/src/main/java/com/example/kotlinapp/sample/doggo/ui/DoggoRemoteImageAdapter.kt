package com.example.kotlinapp.sample.doggo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ListItemDoggoBinding
import com.example.kotlinapp.views.DataBoundPagingAdapter

class DoggoRemoteImageAdapter : DataBoundPagingAdapter<String, ListItemDoggoBinding>(diffCallback){

    override fun createBinding(parent: ViewGroup): ListItemDoggoBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_doggo,
            parent,
            false
        )
    }

    override fun bind(binding: ListItemDoggoBinding, item: String) {
        binding.picture = item
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}