package com.eateasily.codewars.network

import com.eateasily.codewars.models.User
import com.eateasily.codewars.models.UserChallenge
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("users/{query}")
    suspend fun searchUser(@Path("query") query: String): User

    @GET("users/{user}/code-challenges/completed")
    suspend fun getCompletedChallenges(
        @Path("user") user: String,
        @Query("page") page: Int
    ): UserChallenge

    @GET("users/{user}/code-challenges/authored")
    suspend fun getAuthoredChallenges(
        @Path("user") user: String
    ): UserChallenge

}