package com.example.kotlinapp.sample.doggo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentDoggoLoaderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoggoLoaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<FragmentDoggoLoaderBinding>(
            inflater,
            R.layout.fragment_doggo_loader,
            container,
            false
        ).apply {

        }.root
    }
}