package com.faraz.codewars.domain.usecase

import com.faraz.codewars.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetChallengeDetailsUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    operator fun invoke(challengeId: String) = repository.getChallengeDetails(challengeId)
}
