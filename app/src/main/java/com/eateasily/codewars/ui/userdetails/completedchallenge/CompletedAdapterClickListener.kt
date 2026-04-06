package com.eateasily.codewars.ui.userdetails.completedchallenge

import com.eateasily.codewars.domain.model.CompletedChallenge

interface CompletedAdapterClickListener {
    fun itemClicked(data: CompletedChallenge)
}