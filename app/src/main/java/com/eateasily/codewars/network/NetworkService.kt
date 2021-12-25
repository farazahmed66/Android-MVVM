package com.eateasily.codewars.network

import retrofit2.http.GET

interface NetworkService {

    @GET("users/PG1")
    suspend fun getLessonList(): Any
}