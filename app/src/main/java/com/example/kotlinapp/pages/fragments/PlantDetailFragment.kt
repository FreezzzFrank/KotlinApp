package com.example.kotlinapp.pages.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinapp.BuildConfig
import com.example.kotlinapp.databinding.FragmentPlantDetailBinding

class PlantDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantDetailBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }
}