package com.example.kotlinapp.sample.doggo.data.remote

import com.example.kotlinapp.sample.doggo.data.DoggoImageModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface DoggoApi {

    @GET("v1/images/search")
    suspend fun getDoggoImages(@Query("page") page: Int, @Query("limit") size: Int) : List<DoggoImageModel>

    companion object {
        private const val baseUrl = "https://api.thedogapi.com"

        fun create(okHttpClient: OkHttpClient): DoggoApi {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(DoggoApi::class.java)
        }
    }
}