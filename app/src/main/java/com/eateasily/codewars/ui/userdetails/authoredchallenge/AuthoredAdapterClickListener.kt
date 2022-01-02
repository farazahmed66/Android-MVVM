package com.eateasily.codewars.ui.userdetails.authoredchallenge

import com.eateasily.codewars.models.AuthoredChallengeData

interface AuthoredAdapterClickListener {
    fun itemClicked(data: AuthoredChallengeData)
}