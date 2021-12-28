package com.eateasily.codewars.repository

import com.eateasily.codewars.base.BaseRepository
import com.eateasily.codewars.network.NetworkService
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(
    private val networkService: NetworkService,
) : BaseRepository() {


}