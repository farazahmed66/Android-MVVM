package com.eateasily.codewars.data.remote

import com.eateasily.codewars.data.remote.dto.AuthoredChallengeDto
import com.eateasily.codewars.data.remote.dto.ChallengeDetailsDto
import com.eateasily.codewars.data.remote.dto.UserChallengeDto
import com.eateasily.codewars.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("users/{query}")
    suspend fun searchUser(@Path("query") query: String): UserDto

    @GET("users/{user}/code-challenges/completed")
    suspend fun getCompletedChallenge(
        @Path("user") user: String,
        @Query("page") page: Int
    ): UserChallengeDto

    @GET("users/{user}/code-challenges/authored")
    suspend fun getAuthoredChallenge(
        @Path("user") user: String
    ): AuthoredChallengeDto

    @GET("code-challenges/{challengeId}")
    suspend fun getChallengeDetails(
        @Path("challengeId") challengeId: String
    ): ChallengeDetailsDto
}