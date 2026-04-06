package com.faraz.codewars.domain.repository

import androidx.paging.PagingData
import com.faraz.codewars.domain.Resource
import com.faraz.codewars.domain.model.AuthoredChallengeData
import com.faraz.codewars.domain.model.ChallengeDetails
import com.faraz.codewars.domain.model.CompletedChallenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getCompletedChallenges(userName: String): Flow<PagingData<CompletedChallenge>>
    fun getAuthoredChallenges(user: String): Flow<Resource<List<AuthoredChallengeData>>>
    fun getChallengeDetails(challengeId: String): Flow<Resource<ChallengeDetails>>
}
