package com.eateasily.codewars.domain.usecase

import app.cash.turbine.test
import com.eateasily.codewars.domain.Resource
import com.eateasily.codewars.domain.model.RankInfo
import com.eateasily.codewars.domain.model.User
import com.eateasily.codewars.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchUserUseCaseTest {

    private val repository: UserRepository = mockk()
    private val useCase = SearchUserUseCase(repository)

    private val dummyRank = RankInfo(rank = -4, name = "4 kyu", color = "blue", score = 100)
    private val dummyUser = User(
        userName = "g964",
        name = "GG",
        honor = "2340",
        clan = null,
        leaderboardPosition = 12,
        skills = emptyList(),
        overallRank = dummyRank,
        languageRanks = emptyMap(),
        codeChallengesAuthored = 5
    )

    @Test
    fun `delegates to repository with correct query`() = runTest {
        every { repository.searchUser("g964") } returns flowOf(Resource.Success(dummyUser))

        useCase("g964")

        verify(exactly = 1) { repository.searchUser("g964") }
    }

    @Test
    fun `emits Loading then Success from repository`() = runTest {
        every { repository.searchUser("g964") } returns flowOf(
            Resource.Loading,
            Resource.Success(dummyUser)
        )

        useCase("g964").test {
            assertEquals(Resource.Loading, awaitItem())
            assertEquals(Resource.Success(dummyUser), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `emits Failure when repository fails`() = runTest {
        val failure = Resource.Failure(isNetworkError = true, errorCode = null, errorBody = null)
        every { repository.searchUser("unknown") } returns flowOf(Resource.Loading, failure)

        useCase("unknown").test {
            awaitItem() // Loading
            assertEquals(failure, awaitItem())
            awaitComplete()
        }
    }
}
