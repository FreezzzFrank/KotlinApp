package com.example.kotlinapp.sample.doggo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentDoggoRemoteBinding
import com.example.kotlinapp.sample.doggo.data.viewmodel.DoggoRemoteViewModel
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.utilities.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DoggoRemoteFragment : Fragment() {

    @Inject
    lateinit var appExecutors: AppExecutors

    private var adapter by autoCleared<DoggoRemoteImageAdapter>()
    var binding by autoCleared<FragmentDoggoRemoteBinding>()
    private val doggoRemoteViewModel: DoggoRemoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<FragmentDoggoRemoteBinding>(
            inflater,
            R.layout.fragment_doggo_remote,
            container,
            false
        ).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val rvAdapter = DoggoRemoteImageAdapter()
        adapter = rvAdapter
        binding.doggoList.adapter = rvAdapter

        fetchDoggoList()
    }

    private fun fetchDoggoList() {
        doggoRemoteViewModel.fetchDoggoImages().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }
}
