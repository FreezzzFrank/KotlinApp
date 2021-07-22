package com.example.kotlinapp.sample.paging.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentCheeseBinding
import com.example.kotlinapp.sample.paging.ui.adapter.CheeseAdapter
import com.example.kotlinapp.sample.paging.ui.adapter.CheeseViewHolder
import com.example.kotlinapp.sample.paging.viewmodel.CheeseViewModel
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.utilities.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CheeseFragment : Fragment() {

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel: CheeseViewModel by viewModels()
    var binding by autoCleared<FragmentCheeseBinding>()
    private var adapter by autoCleared<CheeseAdapter>()

    private var cheeseJob: Job? = null

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
            initSwipeToDelete()
            addButton.setOnClickListener {
                addCheese()
            }
            inputText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addCheese()
                    return@setOnEditorActionListener true
                }
                false
            }
            inputText.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    addCheese()
                    return@setOnKeyListener true
                }
                false
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAdapter = CheeseAdapter()
        adapter = rvAdapter
        binding.cheeseList.adapter = rvAdapter
        allCheeses()
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val cheeseViewHolder = viewHolder as CheeseViewHolder
                return if (cheeseViewHolder.cheese != null) {
                    makeMovementFlags(0, ItemTouchHelper.RIGHT)
                } else {
                    makeMovementFlags(0, 0)
                }
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseViewHolder).cheese?.let {
                    viewModel.removeCheese(it)
                }
            }
        }).attachToRecyclerView(binding.cheeseList)
    }

    private fun allCheeses() {
        cheeseJob?.cancel()
        cheeseJob = lifecycleScope.launch {
            viewModel.allCheeses.collectLatest { adapter.submitData(it) }
        }
    }

    private fun addCheese() {
        viewModel.setCheese(binding.inputText.text)
    }
}