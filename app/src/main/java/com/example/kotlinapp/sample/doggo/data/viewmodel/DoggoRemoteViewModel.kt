package com.example.kotlinapp.sample.doggo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.kotlinapp.sample.doggo.data.DoggoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DoggoRemoteViewModel @Inject constructor(val repository: DoggoRepository) : ViewModel() {

    fun fetchDoggoImages(): LiveData<PagingData<String>> {
        return repository.letDoggoImagesLiveData().map {
            it.map { doggoImageModel -> doggoImageModel.url }
        }.cachedIn(viewModelScope)
    }
}