package com.example.kotlinapp.sample.paging.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentCheeseBinding
import com.example.kotlinapp.utilities.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheeseFragment : Fragment() {

    var binding by autoCleared<FragmentCheeseBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<FragmentCheeseBinding>(
            inflater,
            R.layout.fragment_cheese,
            container,
            false
        ).apply {
            binding = this
        }.root
    }
}