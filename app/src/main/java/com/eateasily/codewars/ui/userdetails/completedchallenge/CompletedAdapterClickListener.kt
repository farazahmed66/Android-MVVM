package com.eateasily.codewars.ui.userdetails.completedchallenge

import com.eateasily.codewars.models.UserChallengeData

interface CompletedAdapterClickListener {
    fun itemClicked(data: UserChallengeData)
}