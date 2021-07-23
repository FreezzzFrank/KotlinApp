package com.example.kotlinapp.sample.doggo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ActivityDoggoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoggoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDoggoBinding>(this, R.layout.activity_doggo)
    }
}