package com.example.kotlinapp.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinapp.databinding.ActivityMainBinding
import com.example.kotlinapp.sample.githubbrowser.pages.GithubBrowserActivity
import com.example.kotlinapp.pages.activities.GardenActivity
import com.example.kotlinapp.sample.paging.ui.activity.PagingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            startActivity(Intent(this, GardenActivity::class.java))
        }
        binding.btn2.setOnClickListener {
            startActivity(Intent(this, GithubBrowserActivity::class.java))
        }
        binding.btn3.setOnClickListener {
            startActivity(Intent(this, PagingActivity::class.java))
        }
    }
}