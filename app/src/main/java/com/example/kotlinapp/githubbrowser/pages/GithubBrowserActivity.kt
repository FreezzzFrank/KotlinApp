package com.example.kotlinapp.githubbrowser.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ActivityGithubBrowserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubBrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityGithubBrowserBinding>(this, R.layout.activity_github_browser)
    }
}