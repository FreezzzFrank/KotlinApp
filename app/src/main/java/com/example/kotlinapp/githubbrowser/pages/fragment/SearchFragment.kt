package com.example.kotlinapp.githubbrowser.pages.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.R
import com.example.kotlinapp.data.common.RetryCallback
import com.example.kotlinapp.data.remote.Status
import com.example.kotlinapp.databinding.FragmentSearchBinding
import com.example.kotlinapp.githubbrowser.pages.adapter.RepoListAdapter
import com.example.kotlinapp.githubbrowser.viewmodel.SearchViewModel
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.utilities.autoCleared
import com.example.kotlinapp.utilities.dismissKeyboard
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var appExecutors: AppExecutors

    val searchViewModel: SearchViewModel by viewModels()

    var binding by autoCleared<FragmentSearchBinding>()

    var adapter by autoCleared<RepoListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        val rvAdapter = RepoListAdapter(
            appExecutors = appExecutors,
            isFullName = true
        ) { repo ->
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToRepoFragment(repo.owner.login, repo.name)
            )
        }

        binding.query = searchViewModel.query
        adapter = rvAdapter
        binding.repoList.adapter = rvAdapter
        binding.callback = object : RetryCallback {
            override fun retry() {
                searchViewModel.refresh()
            }
        }

        initSearchInputListener()
    }

    private fun initSearchInputListener() {
        binding.input.setOnEditorActionListener { view, actionId, _ ->
            var handle = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                handle = true
            }
            handle
        }

        binding.input.setOnKeyListener { view, keyCode, event ->
            var handle = false
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                handle = true
            }
            handle
        }
    }

    private fun doSearch(v: View) {
        val query = binding.input.text.toString()
        context?.dismissKeyboard(v)
        searchViewModel.setQuery(query)
    }

    private fun initRecyclerView() {
        binding.repoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    searchViewModel.loadNextPage()
                }
            }
        })

        binding.results = searchViewModel.results
        searchViewModel.results.observe(viewLifecycleOwner) { result ->
            Timber.d("result: %s", result.status)
            when (result.status) {
                Status.LOADING -> Timber.d("loading load: %s", result.data)
                Status.SUCCESS -> Timber.d("loading success: %s", result.data)
                Status.ERROR -> Timber.d("loading failed: %s", result.message)
            }
            adapter.submitList(result.data)
        }

    }
}