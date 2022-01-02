package com.eateasily.codewars.repository

import com.eateasily.codewars.base.BaseRepository
import com.eateasily.codewars.network.NetworkService
import javax.inject.Inject

class ChallengeDetailRepository  @Inject constructor(
    private val networkService: NetworkService
) : BaseRepository() {

    suspend fun getChallengeDetails(challengeId: String) = safeApiCall {
        networkService.getChallengeDetails(challengeId)
    }
}