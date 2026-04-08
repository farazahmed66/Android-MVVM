package com.faraz.codewars.presentation.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import com.faraz.codewars.R
import com.faraz.codewars.databinding.ActivityUserListBinding
import com.faraz.codewars.domain.Resource
import com.faraz.codewars.domain.model.RankInfo
import com.faraz.codewars.domain.model.User
import com.faraz.codewars.presentation.base.BaseActivity
import com.faraz.codewars.presentation.ui.userdetails.UserDetailsActivity
import com.paulrybitskyi.persistentsearchview.PersistentSearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : BaseActivity() {

    private val viewModel: UserListViewModel by viewModels()
    private lateinit var binding: ActivityUserListBinding

    private lateinit var persistentSearchView: PersistentSearchView
    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        showContents()
        setToolbarVisible(false)
        persistentSearchView = binding.persistentSearchView

        initSearchView()
        observeData()
    }


    private fun initSearchView() {
        with(persistentSearchView) {
            setOnLeftBtnClickListener {
                persistentSearchView.collapse()
            }

            setOnSearchConfirmedListener { searchView, query ->
                searchView.collapse()
                viewModel.searchUser(query)
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUserDataResponse.collect { res ->
                    when (res) {
                        is Resource.Success -> {
                            isDataLoaded = true
                            setResponse(res.value)
                            showContents()
                        }
                        is Resource.Failure -> {
                            if (!isDataLoaded) {
                                when {
                                    res.errorCode == 404 -> setUserNotFound()
                                    else -> showError(res)
                                }
                            } else {
                                showSnackbarError(res)
                            }
                        }
                        Resource.Loading -> {
                            isDataLoaded = false
                            showProgressBar()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setUserNotFound() {
        showContents()
        binding.cardUser.visibility = View.GONE
        binding.txvNoUser.visibility = View.VISIBLE
        binding.txvNoUser.text = getString(R.string.no_user_found)
    }

    private fun setResponse(user: User) {
        val displayName = user.name.takeUnless { it.isNullOrEmpty() } ?: user.userName

        binding.cardUser.visibility = View.VISIBLE
        binding.txvNoUser.visibility = View.GONE

        binding.txvName.text = displayName
        binding.txvClan.text = user.clan.takeUnless { it.isNullOrEmpty() } ?: "—"
        binding.txvHonor.text = user.honor?.let { "%,d".format(it) } ?: "—"
        binding.txvPosition.text = user.leaderboardPosition?.let { "#%,d".format(it) } ?: "—"
        binding.txvScore.text = "%,d".format(user.overallRank.score)
        binding.txvCompleted.text = user.totalCompleted?.let { "%,d".format(it) } ?: "—"
        binding.txvAuthored.text = user.totalAuthored?.let { "%,d".format(it) } ?: "—"

        setRankBadge(user.overallRank)
        setTopLanguages(user.languageRanks)

        binding.cardUser.setOnClickListener {
            val userName = user.userName ?: return@setOnClickListener
            val intent = Intent(this@UserListActivity, UserDetailsActivity::class.java)
            intent.putExtra(UserDetailsActivity.EXTRA_USER_NAME, userName)
            startActivity(intent)
        }
    }

    private fun setRankBadge(rank: RankInfo) {
        binding.txvRankBadge.text = rank.name
        val color = rankColor(rank.color)
        binding.txvRankBadge.background.setTint(color)
    }

    private fun setTopLanguages(languageRanks: Map<String, RankInfo>) {
        binding.chipGroupLanguages.removeAllViews()
        languageRanks.entries
            .sortedByDescending { it.value.score }
            .take(6)
            .forEach { (lang, info) ->
                val chip = Chip(this).apply {
                    text = "${lang.replaceFirstChar { it.uppercase() }}  ${info.name}"
                    textSize = 11f
                    isClickable = false
                    setChipBackgroundColorResource(R.color.bgChip)
                    setTextColor(rankColor(info.color))
                }
                binding.chipGroupLanguages.addView(chip)
            }
    }

    private fun rankColor(color: String): Int = when (color) {
        "purple" -> getColor(R.color.colorRankPurple)
        "blue"   -> getColor(R.color.colorRankBlue)
        "yellow" -> getColor(R.color.colorRankYellow)
        else     -> getColor(R.color.colorRankWhite)
    }

    override fun tryAgain() {
        viewModel.searchUser(viewModel.lastQuery)
    }
}