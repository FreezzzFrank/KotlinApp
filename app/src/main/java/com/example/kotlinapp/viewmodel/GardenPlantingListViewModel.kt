package com.example.kotlinapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.kotlinapp.data.local.entities.PlantAndGardenPlantings
import com.example.kotlinapp.data.repository.GardenPlantingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GardenPlantingListViewModel @Inject constructor(
    gardenPlantingRepository: GardenPlantingRepository
): ViewModel(){
    val plantAndGardenPlanting: LiveData<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens().asLiveData()
}