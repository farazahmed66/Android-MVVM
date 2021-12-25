package com.eateasily.codewars.repository

import com.eateasily.codewars.base.BaseRepository
import com.eateasily.codewars.network.NetworkService
import javax.inject.Inject

class UserListRepository @Inject constructor(
    private val networkService: NetworkService,
) : BaseRepository() {

    suspend fun getHomeResponse() = safeApiCall {
        networkService.getLessonList()
    }

}