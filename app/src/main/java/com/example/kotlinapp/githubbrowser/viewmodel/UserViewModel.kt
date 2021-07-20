package com.example.kotlinapp.githubbrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.kotlinapp.data.remote.Resource
import com.example.kotlinapp.githubbrowser.repository.GithubUserRepository
import com.example.kotlinapp.githubbrowser.repository.RepoRepository
import com.example.kotlinapp.githubbrowser.repository.db.entities.Repo
import com.example.kotlinapp.githubbrowser.repository.db.entities.User
import com.example.kotlinapp.utilities.AbsentLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    userRepository: GithubUserRepository,
    repoRepository: RepoRepository
) : ViewModel() {
    private val _login = MutableLiveData<String?>()
    val login: LiveData<String?>
        get() = _login
    val repositories: LiveData<Resource<List<Repo>>> = _login.switchMap { login ->
        if (login == null) {
            AbsentLiveData.create()
        } else {
            repoRepository.loadRepos(login)
        }
    }
    val user: LiveData<Resource<User>> = _login.switchMap { login ->
        if (login == null) {
            AbsentLiveData.create()
        } else {
            userRepository.loadUser(login)
        }
    }

    fun setLogin(login: String) {
        if (_login.value != login) {
            _login.value = login
        }
    }

    fun retry() {
        _login.value?.let {
            _login.value = it
        }
    }
}