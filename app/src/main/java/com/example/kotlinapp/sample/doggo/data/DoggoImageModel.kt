package com.example.kotlinapp.sample.doggo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoggoImageModel(@PrimaryKey val id: String, val url: String)