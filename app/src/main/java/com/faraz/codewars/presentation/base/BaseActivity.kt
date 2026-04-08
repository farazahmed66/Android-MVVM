package com.faraz.codewars.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.faraz.codewars.databinding.ActivityBaseBinding
import com.faraz.codewars.domain.Resource
import com.faraz.codewars.presentation.util.toMessage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.isGone

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    private var systemBarInsets = Insets.NONE
    abstract fun tryAgain()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        super.setContentView(binding.root)
        applyInsets()
        initUi()
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            reapplyInsets()
            insets
        }
    }

    private fun reapplyInsets() {
        val toolbarHidden = binding.toolbarTop.isGone
        binding.toolbarTop.updatePadding(top = if (toolbarHidden) 0 else systemBarInsets.top)
        binding.baseContainer.updatePadding(
            top = if (toolbarHidden) systemBarInsets.top else 0,
            bottom = systemBarInsets.bottom
        )
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
        reapplyInsets()
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
        binding.txvError.text = failure.toMessage(this)
        binding.btnRetry.setOnClickListener { tryAgain() }
    }

    fun showSnackbarError(failure: Resource.Failure) {
        Snackbar.make(binding.root, failure.toMessage(this), Snackbar.LENGTH_LONG).show()
    }
}