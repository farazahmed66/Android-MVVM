package com.eateasily.codewars.ui.userdetails

import androidx.lifecycle.ViewModel
import com.eateasily.codewars.repository.UserDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) : ViewModel() {
}