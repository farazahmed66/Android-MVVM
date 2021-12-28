package com.eateasily.codewars.network

import com.eateasily.codewars.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET("users/{query}")
    suspend fun searchUser(@Path("query") query: String): User
}