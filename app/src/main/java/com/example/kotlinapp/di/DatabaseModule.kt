package com.example.kotlinapp.di

import android.content.Context
import com.example.kotlinapp.data.local.AppDataBase
import com.example.kotlinapp.data.local.dao.GardenPlantingDao
import com.example.kotlinapp.data.local.dao.PlantDao
import com.example.kotlinapp.sample.githubbrowser.repository.db.dao.RepoDao
import com.example.kotlinapp.sample.githubbrowser.repository.db.dao.UserDao
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

    @Provides
    fun provideGithubRepoDao(appDataBase: AppDataBase): RepoDao {
        return appDataBase.githubRepoDao()
    }

    @Provides
    fun provideGithubUserDao(appDataBase: AppDataBase): UserDao {
        return appDataBase.githubUserDao()
    }
}