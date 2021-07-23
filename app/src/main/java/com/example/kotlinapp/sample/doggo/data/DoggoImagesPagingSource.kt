package com.example.kotlinapp.sample.doggo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kotlinapp.sample.doggo.data.remote.DoggoApi
import retrofit2.HttpException
import java.io.IOException

class DoggoImagesPagingSource(private val doggoApi: DoggoApi) : PagingSource<Int, DoggoImageModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DoggoImageModel> {
       val page = params.key ?: DoggoRepository.DEFAULT_PAGE_INDEX
        return try {
            val response = doggoApi.getDoggoImages(page, params.loadSize)
            LoadResult.Page(
                response,
                prevKey = if (page == DoggoRepository.DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DoggoImageModel>): Int? {
        return state.anchorPosition
    }

}