package com.faraz.codewars.domain.usecase

import com.faraz.codewars.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(query: String) = repository.searchUser(query)
}
