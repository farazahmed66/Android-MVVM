package com.eateasily.codewars.ui.userdetails.completedchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eateasily.codewars.models.UserChallengeData
import com.eateasily.codewars.network.Resource
import com.eateasily.codewars.repository.AuthoredChallengeRepository
import com.eateasily.codewars.repository.CompletedChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedChallengeViewModel @Inject constructor(
    private val completedChallengeRepository: CompletedChallengeRepository
) : ViewModel() {

    private var currentResult: Flow<PagingData<UserChallengeData>>? = null

    @ExperimentalPagingApi
    fun getCompletedChallenge(userName: String): Flow<PagingData<UserChallengeData>> {
        val newResult: Flow<PagingData<UserChallengeData>> =
            completedChallengeRepository.getCompletedChallenge(userName).cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}