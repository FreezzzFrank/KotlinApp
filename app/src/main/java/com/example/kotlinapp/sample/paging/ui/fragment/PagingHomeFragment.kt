package com.example.kotlinapp.sample.paging.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentPagingHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<FragmentPagingHomeBinding>(
            inflater,
            R.layout.fragment_paging_home,
            container,
            false
        ).apply {
            btn1.setOnClickListener {
                findNavController().navigate(
                    PagingHomeFragmentDirections.actionPagingHomeFragmentToCheeseFragment()
                )
            }
            btn2.setOnClickListener {

            }
            btn3.setOnClickListener {

            }
        }.root
    }
}