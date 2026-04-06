package com.eateasily.codewars.domain.usecase

import com.eateasily.codewars.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetCompletedChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    operator fun invoke(userName: String) = repository.getCompletedChallenges(userName)
}
