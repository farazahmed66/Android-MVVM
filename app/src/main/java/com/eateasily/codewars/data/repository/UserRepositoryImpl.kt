package com.eateasily.codewars.data.repository

import com.eateasily.codewars.data.networkBoundResource
import com.eateasily.codewars.data.local.isExpired
import com.eateasily.codewars.data.local.dao.UserCacheDao
import com.eateasily.codewars.data.local.entity.UserCacheEntity
import com.eateasily.codewars.data.mapper.toDomain
import com.eateasily.codewars.data.remote.NetworkService
import com.eateasily.codewars.data.remote.dto.UserDto
import com.eateasily.codewars.domain.Resource
import com.eateasily.codewars.domain.model.User
import com.eateasily.codewars.domain.repository.UserRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val cacheDao: UserCacheDao,
    private val moshi: Moshi
) : UserRepository {

    private val adapter by lazy { moshi.adapter(UserDto::class.java) }

    override fun searchUser(query: String): Flow<Resource<User>> {
        var cacheEntry: UserCacheEntity? = null
        return networkBoundResource(
            query = {
                cacheEntry = cacheDao.get(query)
                cacheEntry?.let { adapter.fromJson(it.json)?.toDomain() }
            },
            fetch = { networkService.searchUser(query) },
            saveFetchResult = { dto ->
                dto.userName?.let {
                    cacheDao.insert(UserCacheEntity(it, adapter.toJson(dto)))
                }
            },
            mapResult = { it.toDomain() },
            shouldFetch = { cacheEntry == null || cacheEntry!!.isExpired() }
        )
    }
}
