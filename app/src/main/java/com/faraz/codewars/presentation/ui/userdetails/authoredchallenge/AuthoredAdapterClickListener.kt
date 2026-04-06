package com.faraz.codewars.presentation.ui.userdetails.authoredchallenge

import com.faraz.codewars.domain.model.AuthoredChallengeData

interface AuthoredAdapterClickListener {
    fun itemClicked(data: AuthoredChallengeData)
}