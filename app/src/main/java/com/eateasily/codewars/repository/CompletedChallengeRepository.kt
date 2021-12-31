package com.eateasily.codewars.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eateasily.codewars.base.BaseRepository
import com.eateasily.codewars.models.UserChallengeData
import com.eateasily.codewars.network.NetworkService
import com.eateasily.codewars.persistence.AppDatabase
import com.eateasily.codewars.remotemediator.UserRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompletedChallengeRepository @Inject constructor(
    private val networkService: NetworkService,
    private val db: AppDatabase
) : BaseRepository() {

    private val pagingSourceFactory = { db.starWarsDao().getUsers() }

    @ExperimentalPagingApi
    fun getCompletedChallenge(userName: String): Flow<PagingData<UserChallengeData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = UserRemoteMediator(
                networkService,
                db,
                userName
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}