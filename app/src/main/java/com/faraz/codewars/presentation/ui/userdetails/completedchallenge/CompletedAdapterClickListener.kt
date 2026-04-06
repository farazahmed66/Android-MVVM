package com.faraz.codewars.presentation.ui.userdetails.completedchallenge

import com.faraz.codewars.domain.model.CompletedChallenge

interface CompletedAdapterClickListener {
    fun itemClicked(data: CompletedChallenge)
}