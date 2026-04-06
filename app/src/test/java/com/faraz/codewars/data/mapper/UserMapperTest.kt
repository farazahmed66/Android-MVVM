package com.faraz.codewars.data.mapper

import com.faraz.codewars.data.remote.dto.CodeChallengesDto
import com.faraz.codewars.data.remote.dto.OverallDto
import com.faraz.codewars.data.remote.dto.RanksDto
import com.faraz.codewars.data.remote.dto.UserDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UserMapperTest {

    private val overallDto = OverallDto(rank = -4, name = "4 kyu", color = "blue", score = 500)
    private val languageDto = OverallDto(rank = -6, name = "1 kyu", color = "red", score = 1200)

    private val userDto = UserDto(
        userName = "g964",
        name = "GG",
        honor = "2340",
        clan = "Clan X",
        leaderboardPosition = 5,
        skills = listOf("kotlin", "java"),
        ranks = RanksDto(overall = overallDto, languages = mapOf("kotlin" to languageDto)),
        codeChallenges = CodeChallengesDto(totalAuthored = 10)
    )

    @Test
    fun `maps userName correctly`() {
        assertEquals("g964", userDto.toDomain().userName)
    }

    @Test
    fun `maps overallRank correctly`() {
        val rank = userDto.toDomain().overallRank
        assertEquals(-4, rank.rank)
        assertEquals("4 kyu", rank.name)
        assertEquals("blue", rank.color)
        assertEquals(500L, rank.score)
    }

    @Test
    fun `maps languageRanks correctly`() {
        val langRank = userDto.toDomain().languageRanks["kotlin"]!!
        assertEquals(-6, langRank.rank)
        assertEquals("1 kyu", langRank.name)
    }

    @Test
    fun `maps codeChallengesAuthored correctly`() {
        assertEquals(10L, userDto.toDomain().codeChallengesAuthored)
    }

    @Test
    fun `maps null codeChallenges to null authored count`() {
        val dto = userDto.copy(codeChallenges = null)
        assertNull(dto.toDomain().codeChallengesAuthored)
    }
}
