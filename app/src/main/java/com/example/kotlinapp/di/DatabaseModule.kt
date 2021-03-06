package com.example.kotlinapp.di

import android.content.Context
import com.example.kotlinapp.data.AppDataBase
import com.example.kotlinapp.data.dao.GardenPlantingDao
import com.example.kotlinapp.data.dao.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }

    @Provides
    fun providePlantDao(appDataBase: AppDataBase): PlantDao {
        return appDataBase.plantDao()
    }

    @Provides
    fun provideGardenPlantingDao(appDataBase: AppDataBase): GardenPlantingDao {
        return appDataBase.gardenPlantingDao()
    }
}