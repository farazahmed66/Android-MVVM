package com.eateasily.codewars.ui.userdetails.authoredchallenge

import com.eateasily.codewars.domain.model.AuthoredChallengeData

interface AuthoredAdapterClickListener {
    fun itemClicked(data: AuthoredChallengeData)
}