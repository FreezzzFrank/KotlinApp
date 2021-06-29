package com.example.kotlinapp.data.repository

import com.example.kotlinapp.data.dao.GardenPlantingDao
import com.example.kotlinapp.data.entities.GardenPlanting
import javax.inject.Inject

class GardenPlantingRepository @Inject constructor(private val gardenPlantingDao: GardenPlantingDao) {
    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }

    fun isPlanted(plantId: String) =
        gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()
}