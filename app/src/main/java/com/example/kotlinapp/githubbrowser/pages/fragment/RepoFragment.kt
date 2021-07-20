package com.example.kotlinapp.githubbrowser.pages.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kotlinapp.R
import com.example.kotlinapp.data.common.RetryCallback
import com.example.kotlinapp.databinding.FragmentRepoBinding
import com.example.kotlinapp.githubbrowser.pages.adapter.ContributorAdapter
import com.example.kotlinapp.githubbrowser.viewmodel.RepoViewModel
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.utilities.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RepoFragment : Fragment() {

    private val repoViewModel: RepoViewModel by viewModels()
    var binding by autoCleared<FragmentRepoBinding>()
    private var adapter by autoCleared<ContributorAdapter>()
    private val params by navArgs<RepoFragmentArgs>()

    @Inject
    lateinit var appExecutors: AppExecutors

    private fun initContributorList(viewModel: RepoViewModel) {
        viewModel.contributor.observe(viewLifecycleOwner) { listResource ->
            if (listResource?.data != null) {
                adapter.submitList(listResource.data)
            } else {
                adapter.submitList(emptyList())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<FragmentRepoBinding>(
            inflater,
            R.layout.fragment_repo,
            container,
            false
        ).apply {
            retryCallback = object : RetryCallback {
                override fun retry() {
                    repoViewModel.retry()
                }
            }
            repo = repoViewModel.repo
            binding = this
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repoViewModel.setId(params.owner, params.name)

        val adapter = ContributorAdapter(appExecutors) { contributor, imageView ->
            Timber.d("LoginName: %s", contributor.login)
            val extras = FragmentNavigatorExtras(imageView to contributor.login)
            findNavController().navigate(
                RepoFragmentDirections.actionRepoFragmentToUserFragment(
                    contributor.login, contributor.avatarUrl
                ), extras
            )
        }
        this.adapter = adapter
        binding.contributorList.adapter = adapter
        postponeEnterTransition()
        binding.contributorList.doOnPreDraw {
            startPostponedEnterTransition()
        }
        initContributorList(repoViewModel)
    }

}