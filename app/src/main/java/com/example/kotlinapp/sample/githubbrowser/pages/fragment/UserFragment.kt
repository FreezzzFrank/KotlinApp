package com.example.kotlinapp.sample.githubbrowser.pages.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.request.ImageRequest
import coil.request.ImageResult
import com.example.kotlinapp.R
import com.example.kotlinapp.data.common.RetryCallback
import com.example.kotlinapp.databinding.FragmentUserBinding
import com.example.kotlinapp.sample.githubbrowser.pages.adapter.RepoListAdapter
import com.example.kotlinapp.sample.githubbrowser.viewmodel.UserViewModel
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.utilities.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment() {

    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<FragmentUserBinding>()

    private val userViewModel: UserViewModel by viewModels()
    private var adapter by autoCleared<RepoListAdapter>()
    private val params by navArgs<UserFragmentArgs>()

    private fun initRepoList() {
        userViewModel.repositories.observe(viewLifecycleOwner) { repos ->
            adapter.submitList(repos?.data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentUserBinding>(
            layoutInflater,
            R.layout.fragment_user,
            container,
            false
        )
        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                userViewModel.retry()
            }
        }
        this.binding = binding
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        binding.imageRequestListener = object : ImageRequest.Listener {
            override fun onError(request: ImageRequest, throwable: Throwable) {
                super.onError(request, throwable)
                startPostponedEnterTransition()
            }

            override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
                super.onSuccess(request, metadata)
                startPostponedEnterTransition()
            }
        }
        postponeEnterTransition(1, TimeUnit.SECONDS)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel.setLogin(params.login)

        binding.args = params
        binding.user = userViewModel.user
        val rvAdapter = RepoListAdapter(
            appExecutors = appExecutors,
            isFullName = false
        ) {
            findNavController().navigate(UserFragmentDirections.actionUserFragmentToRepoFragment(
                it.owner.login, it.name
            ))
        }
        binding.repoList.adapter = rvAdapter
        this.adapter = rvAdapter
        initRepoList()
    }

}