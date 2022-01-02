package com.eateasily.codewars.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.eateasily.codewars.entity.RemoteKeys
import com.eateasily.codewars.models.UserChallengeData
import com.eateasily.codewars.network.NetworkService
import com.eateasily.codewars.persistence.AppDatabase
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class UserRemoteMediator(
    private val service: NetworkService,
    private val db: AppDatabase,
    private val userName: String
) : RemoteMediator<Int, UserChallengeData>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserChallengeData>
    ): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                if (db.starWarsDao().count() > 0) return MediatorResult.Success(false)
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                getKey()
            }
        }

        if (key != null) {
            if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
        }

        val page: Int = key?.nextKey ?: 0

        try {
            val apiResponse = service.getCompletedChallenge(userName, page)
            val challengeList = apiResponse.data

            val endOfPaginationReached = apiResponse.totalPages == page

            db.withTransaction {
                val nextKey = page + 1

                db.remoteKeysDao().insertKey(
                    RemoteKeys(
                        0,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached
                    )
                )
                db.starWarsDao().insertMultipleUsers(challengeList!!)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey(): RemoteKeys? {
        return db.remoteKeysDao().getKeys().firstOrNull()
    }

}