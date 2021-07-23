package com.example.kotlinapp.sample.doggo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentDoggoRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoggoRoomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<FragmentDoggoRoomBinding>(
            inflater,
            R.layout.fragment_doggo_room,
            container,
            false
        ).root
    }
}