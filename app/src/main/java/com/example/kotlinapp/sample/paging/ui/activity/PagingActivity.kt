package com.example.kotlinapp.sample.paging.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ActivityPagingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityPagingBinding>(this, R.layout.activity_paging)
    }
}