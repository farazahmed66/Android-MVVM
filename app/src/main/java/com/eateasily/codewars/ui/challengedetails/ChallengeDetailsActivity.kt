package com.eateasily.codewars.ui.challengedetails

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.eateasily.codewars.R
import com.eateasily.codewars.base.BaseActivity
import com.eateasily.codewars.databinding.ActivityChallengeDetailsBinding
import com.eateasily.codewars.models.ChallengeDetails
import com.eateasily.codewars.network.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityChallengeDetailsBinding

    private val viewModel: ChallengeDetailsViewModel by viewModels()
    private lateinit var mChallengeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChallengeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mChallengeId = intent.getStringExtra("challenge_id")!!

        initUi()
    }

    private fun initUi() {

        setToolbarVisible(true)
        setToolbarTitle(getString(R.string.challenge_details))

        viewModel.getChallengeDetails(mChallengeId)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launchWhenCreated {

            viewModel.getChallengeDataResponse.collect { res ->
                when (res) {
                    is Resource.Success -> {

                        setResponse(res.value)
                        showContents()
                    }
                    is Resource.Failure -> {
                        showError(res)
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    else -> {

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
        binding.txvLanguages.text = challengeDetails.languages?.joinToString { ", " }
        binding.txvCreatedBy.text = challengeDetails.createdBy?.username
        binding.txvCreatedAt.text = challengeDetails.createdAt
        binding.txvApprovedBy.text = challengeDetails.approvedBy?.username
        binding.txvApprovedAt.text = challengeDetails.approvedAt

    }

    override fun tryAgain() {
        viewModel.getChallengeDetails(mChallengeId)
    }
}