package com.eateasily.codewars.ui.userdetails.authoredchallenge

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
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import com.eateasily.codewars.R
import com.eateasily.codewars.databinding.FragmentAuthoredChallengeBinding
import com.eateasily.codewars.domain.Resource
import com.eateasily.codewars.domain.model.AuthoredChallengeData
import com.eateasily.codewars.ui.challengedetails.ChallengeDetailsActivity
import com.eateasily.codewars.ui.userdetails.UserDetailsActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthoredChallengeFragment : Fragment(), AuthoredAdapterClickListener {

    private val viewModel: AuthoredChallengeViewModel by viewModels()

    private var _binding: FragmentAuthoredChallengeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userName: String
    private lateinit var authoredChallengeAdapter: AuthoredChallengeAdapter

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
        _binding = FragmentAuthoredChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

    }

    private fun initUi() {
        setupRecyclerView()
        observeData()
        viewModel.getAuthoredChallenge(userName)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAuthoredChallenge(userName)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAuthoredChallengeResponse.collect { res ->
                    when (res) {
                        is Resource.Success -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            if (res.value.isNotEmpty()) {
                                binding.progressBar.visibility = View.GONE
                                binding.txvError.visibility = View.GONE
                                binding.rcvAuthoredChallenge.visibility = View.VISIBLE
                                authoredChallengeAdapter.differ.submitList(res.value.reversed())
                            } else {
                                binding.progressBar.visibility = View.GONE
                                binding.txvError.visibility = View.VISIBLE
                                binding.rcvAuthoredChallenge.visibility = View.GONE
                                Snackbar.make(binding.root, getString(R.string.no_data), Snackbar.LENGTH_LONG).show()
                            }
                        }
                        is Resource.Failure -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            if (authoredChallengeAdapter.differ.currentList.isEmpty()) {
                                setError(res)
                            } else {
                                val message = if (res.isNetworkError) getString(R.string.no_internet)
                                              else getString(R.string.something_went_wrong)
                                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                            }
                        }
                        Resource.Loading -> {
                            // Only show full-screen spinner on the initial load (empty list)
                            if (authoredChallengeAdapter.differ.currentList.isEmpty()) {
                                setLoading()
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.txvError.visibility = View.GONE
        binding.rcvAuthoredChallenge.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        authoredChallengeAdapter = AuthoredChallengeAdapter(this)
        binding.rcvAuthoredChallenge.apply {
            adapter = authoredChallengeAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setError(res: Resource.Failure) {
        binding.progressBar.visibility = View.GONE
        binding.txvError.visibility = View.VISIBLE
        binding.rcvAuthoredChallenge.visibility = View.GONE
        if (res.isNetworkError) {
            Snackbar.make(binding.root, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                .show()
        } else {
            Snackbar.make(
                binding.root,
                getText(R.string.something_went_wrong),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun itemClicked(data: AuthoredChallengeData) {
        val intent = Intent(this.context, ChallengeDetailsActivity::class.java)
        intent.putExtra(ChallengeDetailsActivity.EXTRA_CHALLENGE_ID, data.id)
        startActivity(intent)
    }

}