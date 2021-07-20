package com.example.kotlinapp.githubbrowser.repository

import androidx.lifecycle.LiveData
import com.example.kotlinapp.data.remote.NetworkBoundResource
import com.example.kotlinapp.data.remote.Resource
import com.example.kotlinapp.githubbrowser.api.GithubService
import com.example.kotlinapp.githubbrowser.repository.db.dao.UserDao
import com.example.kotlinapp.githubbrowser.repository.db.entities.User
import com.example.kotlinapp.utilities.AppExecutors
import javax.inject.Inject

class GithubUserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userDao: UserDao,
    private val githubService: GithubService
) {

    fun loadUser(login: String) : LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?) = data == null

            override fun loadFromDb() = userDao.findByLogin(login)

            override fun createCall() = githubService.getUser(login)

        }.asLivedata()
    }
}