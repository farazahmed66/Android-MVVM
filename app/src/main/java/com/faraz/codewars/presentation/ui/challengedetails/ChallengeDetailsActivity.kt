package com.faraz.codewars.presentation.ui.challengedetails

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import com.faraz.codewars.R
import com.faraz.codewars.databinding.ActivityChallengeDetailsBinding
import com.faraz.codewars.domain.Resource
import com.faraz.codewars.domain.model.ChallengeDetails
import com.faraz.codewars.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDetailsActivity : BaseActivity() {

    companion object {
        const val EXTRA_CHALLENGE_ID = "challenge_id"
    }

    private lateinit var binding: ActivityChallengeDetailsBinding

    private val viewModel: ChallengeDetailsViewModel by viewModels()
    private lateinit var mChallengeId: String
    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChallengeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mChallengeId = intent.getStringExtra(EXTRA_CHALLENGE_ID) ?: run { finish(); return }

        initUi()
    }

    private fun initUi() {

        setToolbarVisible(true)
        setToolbarTitle(getString(R.string.challenge_details))

        viewModel.getChallengeDetails(mChallengeId)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getChallengeDataResponse.collect { res ->
                    when (res) {
                        is Resource.Success -> {
                            isDataLoaded = true
                            setResponse(res.value)
                            showContents()
                        }
                        is Resource.Failure -> {
                            if (!isDataLoaded) showError(res) else showSnackbarError(res)
                        }
                        is Resource.Loading -> {
                            if (!isDataLoaded) showProgressBar()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setResponse(challengeDetails: ChallengeDetails) {
        binding.layoutContent.visibility = View.VISIBLE

        binding.txvName.text = challengeDetails.name
        binding.txvDesc.text = challengeDetails.description
        binding.txvCategory.text = challengeDetails.category
        binding.txvLanguages.text = challengeDetails.languages?.joinToString(", ") ?: ""
        binding.txvCreatedBy.text = challengeDetails.createdBy?.username ?: ""
        binding.txvCreatedAt.text = challengeDetails.createdAt ?: ""
        binding.txvApprovedBy.text = challengeDetails.approvedBy?.username ?: ""
        binding.txvApprovedAt.text = challengeDetails.approvedAt ?: ""

    }

    override fun tryAgain() {
        viewModel.getChallengeDetails(mChallengeId)
    }
}