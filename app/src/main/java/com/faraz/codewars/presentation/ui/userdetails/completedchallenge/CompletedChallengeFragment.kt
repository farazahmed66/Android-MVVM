package com.faraz.codewars.presentation.ui.userdetails.completedchallenge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.faraz.codewars.R
import com.faraz.codewars.databinding.FragmentCompletedChallengeBinding
import com.faraz.codewars.domain.model.CompletedChallenge
import com.faraz.codewars.presentation.ui.challengedetails.ChallengeDetailsActivity
import com.faraz.codewars.presentation.ui.userdetails.UserDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CompletedChallengeFragment : Fragment(), CompletedAdapterClickListener {

    private var _binding: FragmentCompletedChallengeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompletedChallengeViewModel by viewModels()
    private lateinit var userName: String
    private val adapter = CompletedChallengeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(UserDetailsActivity.EXTRA_USER_NAME) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        setUpAdapter()
        observeLoadState()
        collectPagingData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun collectPagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCompletedChallenge(userName).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun observeLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { loadState ->
                    val b = _binding ?: return@collectLatest

                    if (loadState.mediator?.refresh is LoadState.Loading) {
                        if (adapter.snapshot().isEmpty()) {
                            b.progressBar.visibility = View.VISIBLE
                        }
                        b.txvError.visibility = View.GONE
                    } else {
                        b.progressBar.visibility = View.GONE
                        b.swipeRefreshLayout.isRefreshing = false

                        if (loadState.mediator?.refresh is LoadState.NotLoading) {
                            b.rcvCompletedChallenge.scrollToPosition(0)
                        }

                        val error = when {
                            loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                            loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                            loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error
                            else -> null
                        }
                        error?.let {
                            if (adapter.snapshot().isEmpty()) {
                                b.txvError.visibility = View.VISIBLE
                                b.txvError.text = getString(R.string.something_went_wrong)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        binding.rcvCompletedChallenge.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = this@CompletedChallengeFragment.adapter.withLoadStateFooter(
                footer = CompletedLoadingStateAdapter { this@CompletedChallengeFragment.adapter.retry() }
            )
        }
    }

    override fun onDestroyView() {
        binding.rcvCompletedChallenge.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun itemClicked(data: CompletedChallenge) {
        val intent = Intent(this.context, ChallengeDetailsActivity::class.java)
        intent.putExtra(ChallengeDetailsActivity.EXTRA_CHALLENGE_ID, data.id)
        startActivity(intent)
    }
}