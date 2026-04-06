package com.faraz.codewars.presentation.ui.userdetails.completedchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.faraz.codewars.domain.model.CompletedChallenge
import com.faraz.codewars.domain.usecase.GetCompletedChallengesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CompletedChallengeViewModel @Inject constructor(
    private val getCompletedChallengesUseCase: GetCompletedChallengesUseCase
) : ViewModel() {

    private var currentUserName: String? = null
    private var currentResult: Flow<PagingData<CompletedChallenge>>? = null

    fun getCompletedChallenge(userName: String): Flow<PagingData<CompletedChallenge>> {
        if (userName == currentUserName) currentResult?.let { return it }
        return getCompletedChallengesUseCase(userName)
            .cachedIn(viewModelScope)
            .also { currentResult = it; currentUserName = userName }
    }
}