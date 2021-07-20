package com.example.kotlinapp.githubbrowser.repository.vo

import com.example.kotlinapp.githubbrowser.repository.db.entities.Repo
import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("items")
    val items: List<Repo>,
) {
    var nextPage: Int? = null
}
