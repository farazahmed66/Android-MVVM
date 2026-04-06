package com.eateasily.codewars.domain.repository

import androidx.paging.PagingData
import com.eateasily.codewars.domain.Resource
import com.eateasily.codewars.domain.model.AuthoredChallengeData
import com.eateasily.codewars.domain.model.ChallengeDetails
import com.eateasily.codewars.domain.model.CompletedChallenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getCompletedChallenges(userName: String): Flow<PagingData<CompletedChallenge>>
    fun getAuthoredChallenges(user: String): Flow<Resource<List<AuthoredChallengeData>>>
    fun getChallengeDetails(challengeId: String): Flow<Resource<ChallengeDetails>>
}
