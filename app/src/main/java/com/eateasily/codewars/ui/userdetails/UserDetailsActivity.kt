package com.eateasily.codewars.ui.userdetails

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.eateasily.codewars.R
import com.eateasily.codewars.databinding.ActivityUserDetailsBinding
import com.eateasily.codewars.network.Resource
import com.eateasily.codewars.ui.userdetails.authoredchallenge.AuthoredChallengeFragment
import com.eateasily.codewars.ui.userdetails.completedchallenge.CompletedChallengeFragment
import com.eateasily.codewars.ui.users.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private val viewModel: UserDetailsViewModel by viewModels()

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var mUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra("userName")!!

        bottomNavigation()

        val fragment: Fragment = CompletedChallengeFragment()
        navigate(fragment)

        deleteUserData()
    }

    private fun deleteUserData() {
        lifecycleScope.launchWhenCreated {
            viewModel.deleteUser()
        }
    }

    private fun bottomNavigation() {

        binding.bottomNavigation.setOnItemSelectedListener{ menuItem ->
            when(menuItem.itemId){
                R.id.action_completed -> {
                    val fragment: Fragment = CompletedChallengeFragment()
                    navigate(fragment)

                }
                R.id.action_authored -> {
                    val fragment: Fragment = AuthoredChallengeFragment()
                    navigate(fragment)
                }
            }

            return@setOnItemSelectedListener true
        }
    }

    private fun navigate(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val bundle = Bundle()
        bundle.putString("userName", mUserName);
        fragment.arguments = bundle

        fragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment).commit()
    }
}