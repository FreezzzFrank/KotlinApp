package com.example.kotlinapp.sample.doggo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentDoggoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoggoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<FragmentDoggoBinding>(
            inflater,
            R.layout.fragment_doggo,
            container,
            false
        ).apply {
            btn1.setOnClickListener {
                findNavController().navigate(
                    DoggoFragmentDirections.actionDoggoFragmentToDoggoRemoteFragment()
                )
            }
            btn2.setOnClickListener {
                findNavController().navigate(
                    DoggoFragmentDirections.actionDoggoFragmentToDoggoLoaderFragment()
                )
            }
            btn3.setOnClickListener {
                findNavController().navigate(
                    DoggoFragmentDirections.actionDoggoFragmentToDoggoRoomFragment()
                )
            }
        }.root
    }
}