package com.eateasily.codewars.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.eateasily.codewars.data.local.AppDatabase
import com.eateasily.codewars.data.local.dao.AuthoredChallengeCacheDao
import com.eateasily.codewars.data.local.dao.ChallengeDetailsCacheDao
import com.eateasily.codewars.data.local.entity.AuthoredChallengeCacheEntity
import com.eateasily.codewars.data.local.entity.ChallengeDetailsCacheEntity
import com.eateasily.codewars.data.local.isExpired
import com.eateasily.codewars.data.mapper.toDomain
import com.eateasily.codewars.data.mediator.UserRemoteMediator
import com.eateasily.codewars.data.networkBoundResource
import com.eateasily.codewars.data.remote.NetworkService
import com.eateasily.codewars.data.remote.dto.AuthoredChallengeDto
import com.eateasily.codewars.data.remote.dto.ChallengeDetailsDto
import com.eateasily.codewars.domain.Resource
import com.eateasily.codewars.domain.model.AuthoredChallengeData
import com.eateasily.codewars.domain.model.ChallengeDetails
import com.eateasily.codewars.domain.model.CompletedChallenge
import com.eateasily.codewars.domain.repository.ChallengeRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val db: AppDatabase,
    private val authoredChallengeCacheDao: AuthoredChallengeCacheDao,
    private val challengeDetailsCacheDao: ChallengeDetailsCacheDao,
    private val moshi: Moshi
) : ChallengeRepository {

    private val authoredAdapter by lazy { moshi.adapter(AuthoredChallengeDto::class.java) }
    private val detailsAdapter by lazy { moshi.adapter(ChallengeDetailsDto::class.java) }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCompletedChallenges(userName: String): Flow<PagingData<CompletedChallenge>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
            remoteMediator = UserRemoteMediator(networkService, db, userName),
            pagingSourceFactory = { db.completedChallengeDao().getAll() }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override fun getAuthoredChallenges(user: String): Flow<Resource<List<AuthoredChallengeData>>> {
        var cacheEntry: AuthoredChallengeCacheEntity? = null
        return networkBoundResource(
            query = {
                cacheEntry = authoredChallengeCacheDao.get(user)
                cacheEntry?.let {
                    authoredAdapter.fromJson(it.json)?.data?.map { dto -> dto.toDomain() }
                }
            },
            fetch = { networkService.getAuthoredChallenge(user) },
            saveFetchResult = { dto ->
                authoredChallengeCacheDao.insert(
                    AuthoredChallengeCacheEntity(user, authoredAdapter.toJson(dto))
                )
            },
            mapResult = { dto -> dto.data.map { it.toDomain() } },
            shouldFetch = { cacheEntry == null || cacheEntry!!.isExpired() }
        )
    }

    override fun getChallengeDetails(challengeId: String): Flow<Resource<ChallengeDetails>> {
        var cacheEntry: ChallengeDetailsCacheEntity? = null
        return networkBoundResource(
            query = {
                cacheEntry = challengeDetailsCacheDao.get(challengeId)
                cacheEntry?.let { detailsAdapter.fromJson(it.json)?.toDomain() }
            },
            fetch = { networkService.getChallengeDetails(challengeId) },
            saveFetchResult = { dto ->
                challengeDetailsCacheDao.insert(
                    ChallengeDetailsCacheEntity(challengeId, detailsAdapter.toJson(dto))
                )
            },
            mapResult = { it.toDomain() },
            shouldFetch = { cacheEntry == null || cacheEntry!!.isExpired() }
        )
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 200
    }
}
