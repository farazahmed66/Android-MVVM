package com.faraz.codewars.presentation.ui.challengedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraz.codewars.domain.Resource
import com.faraz.codewars.domain.model.ChallengeDetails
import com.faraz.codewars.domain.usecase.GetChallengeDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailsViewModel @Inject constructor(
    private val getChallengeDetailsUseCase: GetChallengeDetailsUseCase
) : ViewModel() {

    private val _challengeDetailsResponse =
        MutableStateFlow<Resource<ChallengeDetails>>(Resource.Empty)
    val getChallengeDataResponse: StateFlow<Resource<ChallengeDetails>> = _challengeDetailsResponse

    fun getChallengeDetails(challengeId: String) {
        viewModelScope.launch {
            getChallengeDetailsUseCase(challengeId)
                .collect { _challengeDetailsResponse.value = it }
        }
    }
}