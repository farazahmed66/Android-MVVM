package com.eateasily.codewars.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.eateasily.codewars.R
import com.eateasily.codewars.base.BaseActivity
import com.eateasily.codewars.databinding.ActivityUserListBinding
import com.eateasily.codewars.models.User
import com.eateasily.codewars.network.Resource
import com.eateasily.codewars.ui.userdetails.UserDetailsActivity
import com.paulrybitskyi.persistentsearchview.PersistentSearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : BaseActivity() {

    private val viewModel: UserListViewModel by viewModels()
    private lateinit var binding: ActivityUserListBinding

    private lateinit var persistentSearchView: PersistentSearchView
    private var searchQuery = ""

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
                searchQuery = query
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launchWhenCreated {
            viewModel.getUserDataResponse.collect { res ->
                when (res) {
                    is Resource.Success -> {
                        setResponse(res.value)
                        showContents()
                    }

                    is Resource.Failure -> {
                        when {
                            res.isNetworkError -> {
                                showError(res)
                            }
                            res.errorCode == 404 -> {
                                setUserNotFound()
                            }
                            else -> {
                                showError(res)
                            }
                        }
                    }

                    Resource.Loading -> {
                        showProgressBar()
                    }
                    else -> {

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
        binding.cardUser.visibility = View.VISIBLE
        binding.txvNoUser.visibility = View.GONE
        binding.txvName.text = "Name: ${user.name}"
        binding.txvClan.text = "Clan: ${user.clan}"
        binding.txvHonor.text = "Honor: ${user.honor}"
        binding.txvPosition.text = "Position: ${user.leaderboardPosition}"

        binding.cardUser.setOnClickListener {
            val intent = Intent(this@UserListActivity, UserDetailsActivity::class.java)
            intent.putExtra("userName", user.userName)
            startActivity(intent)
        }
    }

    override fun tryAgain() {
        viewModel.searchUser(searchQuery)
    }
}