package com.example.kotlinapp.pages.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentGardenBinding
import com.example.kotlinapp.pages.adapters.GardenPlantingAdapter
import com.example.kotlinapp.pages.adapters.PLANT_LIST_PAGE_INDEX
import com.example.kotlinapp.viewmodel.GardenPlantingListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class GardenFragment : Fragment() {

    private val viewModel: GardenPlantingListViewModel by viewModels()

    private var _binding: FragmentGardenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter
        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        subscribeUi(adapter, binding)
        return binding.root

    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlanting.observe(viewLifecycleOwner) { result ->
            run {
                binding.hasPlantings = !result.isNullOrEmpty()
                adapter.submitList(result)
            }
        }
    }

    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem = PLANT_LIST_PAGE_INDEX
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}