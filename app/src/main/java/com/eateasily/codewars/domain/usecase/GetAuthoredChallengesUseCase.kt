package com.eateasily.codewars.domain.usecase

import com.eateasily.codewars.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetAuthoredChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    operator fun invoke(user: String) = repository.getAuthoredChallenges(user)
}
