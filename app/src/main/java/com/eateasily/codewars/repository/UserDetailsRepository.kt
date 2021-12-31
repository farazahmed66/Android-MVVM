package com.eateasily.codewars.repository

import com.eateasily.codewars.base.BaseRepository
import com.eateasily.codewars.network.NetworkService
import com.eateasily.codewars.persistence.AppDatabase
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val db: AppDatabase
) : BaseRepository() {

    suspend fun deleteUserData(){
        db.starWarsDao().clearRepos()
        db.remoteKeysDao().clearRemoteKeys()
    }

}