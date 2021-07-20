package com.example.kotlinapp.githubbrowser.repository.db.entities

import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.kotlinapp.data.local.converters.Converters

@Entity(primaryKeys = ["query"])
data class RepoSearchResult(
    val query: String,
    val repoIds: List<Int>,
    val totalCount: Int,
    val next: Int?,
)
