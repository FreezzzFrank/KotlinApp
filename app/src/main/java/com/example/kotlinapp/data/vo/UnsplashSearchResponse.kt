package com.example.kotlinapp.data.vo

import com.google.gson.annotations.SerializedName

data class UnsplashSearchResponse(
    @field:SerializedName("results") val results: List<UnsplashPhoto>,
    @field:SerializedName("total_pages") val totalPages: Int
)