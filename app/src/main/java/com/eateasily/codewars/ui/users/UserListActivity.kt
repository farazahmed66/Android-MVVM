package com.eateasily.codewars.ui.users

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.eateasily.codewars.R
import com.eateasily.codewars.network.Resource
import com.eateasily.codewars.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserListActivity : BaseActivity() {

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)


        viewModel.homeDataRequest()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launchWhenCreated {

            viewModel.getHomeDataResponse.collect { res ->
                when (res) {
                    is Resource.Success -> {

                        //Success Response Play with it
                    }

                    is Resource.Failure -> {

                        //If Any Failure
                    }

                    Resource.Loading -> {
                        //Loading State
                    }
                    else -> {

                    }
                }
            }

        }
    }
}