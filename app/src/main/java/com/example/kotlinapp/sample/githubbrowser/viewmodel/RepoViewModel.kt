package com.example.kotlinapp.sample.githubbrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.kotlinapp.data.remote.Resource
import com.example.kotlinapp.sample.githubbrowser.repository.RepoRepository
import com.example.kotlinapp.sample.githubbrowser.repository.db.entities.Contributor
import com.example.kotlinapp.sample.githubbrowser.repository.db.entities.Repo
import com.example.kotlinapp.utilities.AbsentLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(repository: RepoRepository) : ViewModel() {
    private val _repoId: MutableLiveData<RepoId> = MutableLiveData()
    val repoId: LiveData<RepoId>
        get() = _repoId

    val repo: LiveData<Resource<Repo>> = _repoId.switchMap { input ->
        input.ifExists { owner, name ->
            repository.loadRepo(owner, name)
        }
    }

    val contributor: LiveData<Resource<List<Contributor>>> = _repoId.switchMap { input ->
        input.ifExists{ owner, name ->
            repository.loadContributors(owner, name)
        }
    }

    fun retry() {
        val owner = _repoId.value?.owner
        val name = _repoId.value?.name
        if (owner != null && name != null) {
            _repoId.value = RepoId(owner, name)
        }
    }

    fun setId(owner: String, name: String) {
        val  update = RepoId(owner, name)
        if (_repoId.value == update) {
            return
        }
        _repoId.value = update
    }

    data class RepoId(val owner: String, val name: String) {
        fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
            return if (owner.isBlank() || name.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(owner, name)
            }
        }
    }
}