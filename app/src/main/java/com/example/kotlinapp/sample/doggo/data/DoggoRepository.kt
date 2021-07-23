package com.example.kotlinapp.sample.doggo.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.kotlinapp.data.local.AppDataBase
import com.example.kotlinapp.sample.doggo.data.remote.DoggoApi
import javax.inject.Inject

class DoggoRepository @Inject constructor(
    appDataBase: AppDataBase,
    private val doggoApi: DoggoApi,
) {

    fun letDoggoImagesLiveData(pagingConfig: PagingConfig = getDefaultPagingConfig()): LiveData<PagingData<DoggoImageModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
               DoggoImagesPagingSource(doggoApi)
            }
        ).liveData
    }

    private fun getDefaultPagingConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }
}