package com.example.kotlinapp.di

import com.example.kotlinapp.data.remote.api.UnsplashService
import com.example.kotlinapp.sample.doggo.data.remote.DoggoApi
import com.example.kotlinapp.sample.githubbrowser.api.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideUnsplashService(): UnsplashService {
        return UnsplashService.create()
    }

    @Singleton
    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): GithubService {
        return GithubService.create(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideDoggoService(okHttpClient: OkHttpClient): DoggoApi {
        return DoggoApi.create(okHttpClient)
    }

}