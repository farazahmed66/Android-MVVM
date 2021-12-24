package com.eateasily.codewars.ui.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eateasily.codewars.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
    }
}