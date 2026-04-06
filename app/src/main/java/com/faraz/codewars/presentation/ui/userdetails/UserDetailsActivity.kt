package com.faraz.codewars.presentation.ui.userdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.faraz.codewars.R
import com.faraz.codewars.presentation.base.BaseActivity
import com.faraz.codewars.databinding.ActivityUserDetailsBinding
import com.faraz.codewars.presentation.ui.userdetails.authoredchallenge.AuthoredChallengeFragment
import com.faraz.codewars.presentation.ui.userdetails.completedchallenge.CompletedChallengeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : BaseActivity() {

    companion object {
        const val EXTRA_USER_NAME = "userName"
    }

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var mUserName: String

    private val completedFragment by lazy { CompletedChallengeFragment().withArgs(mUserName) }
    private val authoredFragment by lazy { AuthoredChallengeFragment().withArgs(mUserName) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(EXTRA_USER_NAME) ?: run { finish(); return }

        initUi()
    }

    private fun initUi() {
        showContents()
        setToolbarVisible(true)
        setToolbarTitle(mUserName)
        showFragment(completedFragment)

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_completed -> showFragment(completedFragment)
                R.id.action_authored -> showFragment(authoredFragment)
            }
            true
        }
    }

    private fun showFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val tag = fragment::class.java.simpleName
        val tx = fm.beginTransaction()
        fm.fragments.forEach { tx.hide(it) }
        if (fm.findFragmentByTag(tag) == null) {
            tx.add(R.id.frame_layout, fragment, tag)
        } else {
            tx.show(fragment)
        }
        tx.commit()
    }

    private fun Fragment.withArgs(userName: String): Fragment {
        arguments = Bundle().apply { putString(EXTRA_USER_NAME, userName) }
        return this
    }

    override fun tryAgain() {}
}