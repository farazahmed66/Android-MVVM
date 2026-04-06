package com.faraz.codewars.presentation.ui.userdetails.authoredchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraz.codewars.domain.Resource
import com.faraz.codewars.domain.model.AuthoredChallengeData
import com.faraz.codewars.domain.usecase.GetAuthoredChallengesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthoredChallengeViewModel @Inject constructor(
    private val getAuthoredChallengesUseCase: GetAuthoredChallengesUseCase
) : ViewModel() {

    private val _authoredChallengeResponse =
        MutableStateFlow<Resource<List<AuthoredChallengeData>>>(Resource.Empty)
    val getAuthoredChallengeResponse: StateFlow<Resource<List<AuthoredChallengeData>>> =
        _authoredChallengeResponse

    private var fetchJob: Job? = null

    fun getAuthoredChallenge(user: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getAuthoredChallengesUseCase(user)
                .collect { _authoredChallengeResponse.value = it }
        }
    }
}
