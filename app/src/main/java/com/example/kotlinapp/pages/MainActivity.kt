package com.example.kotlinapp.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ActivityMainBinding
import com.example.kotlinapp.githubbrowser.pages.GithubBrowserActivity
import com.example.kotlinapp.pages.activities.GardenActivity

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
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
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_garden)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}