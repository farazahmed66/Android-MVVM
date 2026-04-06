package com.eateasily.codewars.domain.repository

import com.eateasily.codewars.domain.Resource
import com.eateasily.codewars.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun searchUser(query: String): Flow<Resource<User>>
}
