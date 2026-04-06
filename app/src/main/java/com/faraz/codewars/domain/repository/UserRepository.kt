package com.faraz.codewars.domain.repository

import com.faraz.codewars.domain.Resource
import com.faraz.codewars.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun searchUser(query: String): Flow<Resource<User>>
}
