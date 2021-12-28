package com.eateasily.codewars.ui.userdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eateasily.codewars.databinding.ActivityUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}