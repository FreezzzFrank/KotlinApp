package com.example.kotlinapp.sample.githubbrowser.api

import androidx.lifecycle.LiveData
import com.example.kotlinapp.data.remote.LiveDataCallAdapterFactory
import com.example.kotlinapp.data.remote.api.ApiResponse
import com.example.kotlinapp.sample.githubbrowser.repository.db.entities.Contributor
import com.example.kotlinapp.sample.githubbrowser.repository.db.entities.Repo
import com.example.kotlinapp.sample.githubbrowser.repository.db.entities.User
import com.example.kotlinapp.sample.githubbrowser.repository.vo.RepoSearchResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): LiveData<ApiResponse<RepoSearchResponse>>

    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String, @Query("page") page: Int): Call<RepoSearchResponse>

    @GET("users/{login}")
    fun getUser(@Path("login") login: String) : LiveData<ApiResponse<User>>

    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String) : LiveData<ApiResponse<List<Repo>>>

    @GET("repos/{owner}/{name}")
    fun getRepo(
        @Path("owner") owner: String,
        @Path("name") name: String
    ) : LiveData<ApiResponse<Repo>>

    @GET("repos/{owner}/{name}/contributors")
    fun getContributors(
        @Path("owner") owner: String,
        @Path("name") name: String
    ) : LiveData<ApiResponse<List<Contributor>>>

    companion object {
        private const val baseUrl = "https://api.github.com/"

        fun create(okHttpClient: OkHttpClient): GithubService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(GithubService::class.java)
        }
    }
}