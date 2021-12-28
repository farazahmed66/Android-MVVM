package com.eateasily.codewars.ui.users

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.eateasily.codewars.R
import com.eateasily.codewars.base.BaseActivity
import com.eateasily.codewars.databinding.ActivityUserListBinding
import com.eateasily.codewars.models.User
import com.eateasily.codewars.network.Resource
import com.google.android.material.snackbar.Snackbar
import com.paulrybitskyi.persistentsearchview.PersistentSearchView
import com.paulrybitskyi.persistentsearchview.adapters.model.SuggestionItem
import com.paulrybitskyi.persistentsearchview.listeners.OnSuggestionChangeListener
import com.paulrybitskyi.persistentsearchview.utils.VoiceRecognitionDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : BaseActivity() {

    private val viewModel: UserListViewModel by viewModels()
    private lateinit var binding: ActivityUserListBinding
    private lateinit var persistentSearchView: PersistentSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
//        setSupportActionBar(binding.toolbar)
        persistentSearchView = binding.persistentSearchView

        initSearchView()


        observeData()
    }


    private fun initSearchView() {
        with(persistentSearchView) {
            setOnLeftBtnClickListener {
                persistentSearchView.collapse()
            }
            setOnClearInputBtnClickListener {
                // Handle the clear input button click
            }

            // Setting a delegate for the voice recognition input
            setVoiceRecognitionDelegate(VoiceRecognitionDelegate(this@UserListActivity))

            setOnSearchConfirmedListener { searchView, query ->
                searchView.collapse()
                viewModel.searchUser(query)
//                persistentSearchView.setSuggestions(SuggestionCreationUtil.asRecentSearchSuggestions(searchQueries), false)

            }

            setOnSearchQueryChangeListener { searchView, oldQuery, newQuery ->
                // Handle a search query change. This is the place where you'd
                // want load new suggestions based on the newQuery parameter.
            }

            setOnSuggestionChangeListener(object : OnSuggestionChangeListener {

                override fun onSuggestionPicked(suggestion: SuggestionItem) {
                    // Handle a suggestion pick event. This is the place where you'd
                    // want to perform a search against your data provider.
                }

                override fun onSuggestionRemoved(suggestion: SuggestionItem) {
                    // Handle a suggestion remove event. This is the place where
                    // you'd want to remove the suggestion from your data provider.
                }

            })
        }
    }


    private fun observeData() {
        lifecycleScope.launchWhenCreated {

            viewModel.getHomeDataResponse.collect { res ->
                when (res) {
                    is Resource.Success -> {

                        setResponse(res.value)
                        //Success Response Play with it
                        Log.d("response", res.toString())
                    }

                    is Resource.Failure -> {
                        setError(res)

                        Log.d("responseError", res.toString())
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

    private fun setError(res: Resource.Failure) {
        binding.cardUser.visibility = View.GONE
        if(res.isNetworkError){
            Snackbar.make(binding.root, "Please Check connectivity.", Snackbar.LENGTH_LONG).show()
        }else{
            Snackbar.make(binding.root, "Something went wrong.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setResponse(user: User) {
        binding.cardUser.visibility = View.VISIBLE
        binding.txvName.text = "Name: ${user.name}"
        binding.txvClan.text = "Clan: ${user.clan}"
        binding.txvHonor.text = "Honor: ${user.honor}"
        binding.txvPosition.text = "Position: ${user.leaderboardPosition}"
    }

}