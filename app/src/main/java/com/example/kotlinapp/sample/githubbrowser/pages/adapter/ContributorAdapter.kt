package com.example.kotlinapp.sample.githubbrowser.pages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ListItemContributorBinding
import com.example.kotlinapp.sample.githubbrowser.repository.db.entities.Contributor
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.views.DataBoundListAdapter

class ContributorAdapter(
    appExecutors: AppExecutors,
    private val callback: ((Contributor, ImageView) -> Unit)?
): DataBoundListAdapter<Contributor, ListItemContributorBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Contributor>() {
        override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.avatarUrl == newItem.avatarUrl
                    && oldItem.contributions == newItem.contributions
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ListItemContributorBinding {
        val binding = DataBindingUtil.inflate<ListItemContributorBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_contributor,
            parent,
            false
        )

        binding.root.setOnClickListener { _ ->
            binding.contributor?.let {
                callback?.invoke(it, binding.imageView)
            }
        }
        return binding
    }

    override fun bind(binding: ListItemContributorBinding, item: Contributor) {
        binding.contributor = item
    }
}