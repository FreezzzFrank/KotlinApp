package com.example.kotlinapp.sample.githubbrowser.pages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ListItemRepoBinding
import com.example.kotlinapp.sample.githubbrowser.repository.db.entities.Repo
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.views.DataBoundListAdapter

class RepoListAdapter(
    appExecutors: AppExecutors,
    private val isFullName: Boolean,
    private val repoClickCallback: ((Repo) -> Unit)?
) : DataBoundListAdapter<Repo, ListItemRepoBinding>(
    appExecutors,
    object : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.owner == newItem.owner
                    && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.description == newItem.description
                    && oldItem.stars == newItem.stars
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ListItemRepoBinding {
        return DataBindingUtil.inflate<ListItemRepoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_repo,
            parent,
            false
        ).apply {
            showFullName = isFullName
            root.setOnClickListener { _ ->
                repo?.let {
                    repoClickCallback?.invoke(it)
                }
            }
        }
    }

    override fun bind(binding: ListItemRepoBinding, item: Repo) {
        binding.repo = item
    }
}