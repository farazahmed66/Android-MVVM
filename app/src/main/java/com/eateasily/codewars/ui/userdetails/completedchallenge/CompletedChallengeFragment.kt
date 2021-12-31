package com.eateasily.codewars.ui.userdetails.completedchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.eateasily.codewars.databinding.FragmentCompletedChallengeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


@AndroidEntryPoint
class CompletedChallengeFragment : Fragment() {

    private var _binding: FragmentCompletedChallengeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompletedChallengeViewModel by viewModels()
    private lateinit var userName: String
    private var searchJob: Job? = null
    private val adapter =
        CompletedChallengeAdapter { name: String -> snackBarClickedPlayer(name) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            userName = it.getString("userName")!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        startSearchJob()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun snackBarClickedPlayer(name: String) {
//        val parentLayout = findViewById<View>(android.R.id.content)
//        Snackbar.make(parentLayout, name, Snackbar.LENGTH_LONG)
//            .show()
    }


    @ExperimentalPagingApi
    private fun startSearchJob() {
        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            viewModel.getCompletedChallenge(userName)
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    private fun setUpAdapter() {

        binding.rcvCompletedChallenge.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
//            addItemDecoration(RecyclerViewItemDecoration())
        }
        binding.rcvCompletedChallenge.adapter = adapter.withLoadStateFooter(
            footer = CompletedLoadingStateAdapter { retry() }
        )

        adapter.addLoadStateListener { loadState ->

            if (loadState.mediator?.refresh is LoadState.Loading) {

                if (adapter.snapshot().isEmpty()) {
//                    binding.progress.isVisible = true
                }
//                binding.errorTxt.isVisible = false

            } else {
//                binding.progress.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false

                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (adapter.snapshot().isEmpty()) {
//                        binding.errorTxt.isVisible = true
//                        binding.errorTxt.text = it.error.localizedMessage
                    }

                }

            }
        }
    }

    fun getDateTimeFormat(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return formatter.format(parser.parse("2018-12-14T09:55:00"))
    }

    private fun retry() {
        adapter.retry()
    }

}