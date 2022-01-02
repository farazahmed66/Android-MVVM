package com.eateasily.codewars.ui.challengedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eateasily.codewars.models.ChallengeDetails
import com.eateasily.codewars.models.User
import com.eateasily.codewars.network.Resource
import com.eateasily.codewars.repository.ChallengeDetailRepository
import com.eateasily.codewars.repository.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailsViewModel @Inject constructor(
    private val challengeDetailRepository: ChallengeDetailRepository
) : ViewModel() {

    private val _challengeDetailsResponse = MutableStateFlow<Resource<ChallengeDetails>>(Resource.Empty)
    val getChallengeDataResponse: StateFlow<Resource<ChallengeDetails>> = _challengeDetailsResponse


    fun getChallengeDetails(challengeId: String) = viewModelScope.launch {
        _challengeDetailsResponse.value = Resource.Loading
        _challengeDetailsResponse.value = challengeDetailRepository.getChallengeDetails(challengeId)

    }
}