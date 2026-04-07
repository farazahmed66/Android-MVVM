package com.faraz.codewars.presentation.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import com.faraz.codewars.R
import com.faraz.codewars.databinding.ActivityUserListBinding
import com.faraz.codewars.domain.Resource
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
                            if (!isDataLoaded) showProgressBar()
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
        binding.txvName.text = getString(R.string.label_name, displayName)
        binding.txvClan.text = getString(R.string.label_clan, user.clan)
        binding.txvHonor.text = getString(R.string.label_honor, user.honor)
        binding.txvPosition.text = getString(R.string.label_position, user.leaderboardPosition)

        binding.cardUser.setOnClickListener {
            val userName = user.userName ?: return@setOnClickListener
            val intent = Intent(this@UserListActivity, UserDetailsActivity::class.java)
            intent.putExtra(UserDetailsActivity.EXTRA_USER_NAME, userName)
            startActivity(intent)
        }
    }

    override fun tryAgain() {
        viewModel.searchUser(viewModel.lastQuery)
    }
}