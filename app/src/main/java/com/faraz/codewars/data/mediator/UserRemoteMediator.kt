package com.faraz.codewars.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.faraz.codewars.data.local.AppDatabase
import com.faraz.codewars.data.local.entity.CompletedChallengeEntity
import com.faraz.codewars.data.local.entity.RemoteKeysEntity
import com.faraz.codewars.data.mapper.toEntity
import com.faraz.codewars.data.remote.NetworkService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class UserRemoteMediator(
    private val service: NetworkService,
    private val db: AppDatabase,
    private val userName: String
) : RemoteMediator<Int, CompletedChallengeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CompletedChallengeEntity>
    ): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                db.withTransaction {
                    db.completedChallengeDao().clearAll()
                    db.remoteKeysDao().clearRemoteKeys()
                }
                null
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> getKey()
        }

        if (key != null && key.isEndReached) {
            return MediatorResult.Success(endOfPaginationReached = true)
        }

        val page: Int = key?.nextKey ?: 0

        return try {
            val apiResponse = service.getCompletedChallenge(userName, page)
            val challengeList = apiResponse.data

            val endOfPaginationReached = page + 1 >= apiResponse.totalPages

            db.withTransaction {
                db.remoteKeysDao().insertKey(
                    RemoteKeysEntity(
                        id = 0,
                        nextKey = page + 1,
                        isEndReached = endOfPaginationReached
                    )
                )
                db.completedChallengeDao().insertAll(
                    challengeList?.map { it.toEntity() } ?: emptyList()
                )
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey(): RemoteKeysEntity? {
        return db.remoteKeysDao().getKeys().firstOrNull()
    }
}
