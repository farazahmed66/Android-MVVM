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

class AuthoredChallengeRepository @Inject constructor(
    private val networkService: NetworkService,
    private val db: AppDatabase
) : BaseRepository() {



}