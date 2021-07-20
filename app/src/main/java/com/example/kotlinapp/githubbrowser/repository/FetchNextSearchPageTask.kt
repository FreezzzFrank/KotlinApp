package com.example.kotlinapp.githubbrowser.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinapp.data.local.AppDataBase
import com.example.kotlinapp.data.remote.Resource
import com.example.kotlinapp.data.remote.api.ApiEmptyResponse
import com.example.kotlinapp.data.remote.api.ApiErrorResponse
import com.example.kotlinapp.data.remote.api.ApiResponse
import com.example.kotlinapp.data.remote.api.ApiSuccessResponse
import com.example.kotlinapp.githubbrowser.api.GithubService
import com.example.kotlinapp.githubbrowser.repository.db.entities.RepoSearchResult
import java.io.IOException

class FetchNextSearchPageTask(
    private val query: String,
    private val githubService: GithubService,
    private val db: AppDataBase,
) : Runnable {
    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    @SuppressLint("NullSafeMutableLiveData")
    override fun run() {
        val current = db.githubRepoDao().findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        if (nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }

        val newValue = try {
            val response = githubService.searchRepos(query, nextPage).execute()
            when (val apiResponse = ApiResponse.create(response)) {
                is ApiSuccessResponse -> {
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.repoIds)

                    ids.addAll(apiResponse.body.items.map { it.id })
                    val merged = RepoSearchResult(
                        query, ids, apiResponse.body.total, apiResponse.nextPage
                    )
                    db.runInTransaction {
                        db.githubRepoDao().insertSearchResult(merged)
                        db.githubRepoDao().insertRepos(apiResponse.body.items)
                    }
                    Resource.success(apiResponse.nextPage != null)
                }
                is ApiEmptyResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }
                else -> Resource.error(DEFAULT_MSG, true)

            }

        } catch (e: IOException) {
            Resource.error(e.message!!, true)
        }

        _liveData.postValue(newValue)
    }

    companion object {
        const val DEFAULT_MSG = "unknown error"
    }
}