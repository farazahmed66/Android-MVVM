package com.eateasily.codewars.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.eateasily.codewars.R
import com.eateasily.codewars.databinding.ActivityBaseBinding
import com.eateasily.codewars.domain.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    abstract fun tryAgain()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        super.setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        binding.imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun setContentView(layoutResID: Int) {
        binding.baseContainer.removeAllViews()
        LayoutInflater.from(this).inflate(layoutResID, binding.baseContainer, true)
    }

    override fun setContentView(view: View?) {
        binding.baseContainer.removeAllViews()
        binding.baseContainer.addView(view)
    }

    fun setToolbarVisible(isVisible: Boolean) {
        binding.toolbarTop.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setToolbarTitle(title: String) {
        binding.toolbarTitle.text = title
    }

    fun showContents() {
        binding.baseContainer.visibility = View.VISIBLE
        binding.layoutError.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    fun showProgressBar() {
        binding.baseContainer.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    fun showError(failure: Resource.Failure) {
        binding.progressBar.visibility = View.GONE
        binding.baseContainer.visibility = View.GONE
        binding.layoutError.visibility = View.VISIBLE

        binding.txvError.text = if (failure.isNetworkError)
            getString(R.string.no_internet)
        else
            getString(R.string.something_went_wrong)

        binding.btnRetry.setOnClickListener { tryAgain() }
    }

    fun showSnackbarError(failure: Resource.Failure) {
        val message = if (failure.isNetworkError) getString(R.string.no_internet)
                      else getString(R.string.something_went_wrong)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}