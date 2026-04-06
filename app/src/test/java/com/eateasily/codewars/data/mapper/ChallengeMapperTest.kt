package com.eateasily.codewars.data.mapper

import com.eateasily.codewars.data.local.entity.CompletedChallengeEntity
import com.eateasily.codewars.data.remote.dto.AuthoredChallengeDataDto
import com.eateasily.codewars.data.remote.dto.ChallengeDetailsDto
import com.eateasily.codewars.data.remote.dto.CompletedChallengeDto
import com.eateasily.codewars.data.remote.dto.CreatedByDto
import com.eateasily.codewars.data.remote.dto.RankDto
import com.eateasily.codewars.data.remote.dto.UnResolvedDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ChallengeMapperTest {

    // ---- CompletedChallenge ----

    @Test
    fun `CompletedChallengeDto maps to entity correctly`() {
        val dto = CompletedChallengeDto(id = "abc", name = "Test", slug = "test", completedAt = "2024-01-01")
        val entity = dto.toEntity()
        assertEquals("abc", entity.id)
        assertEquals("Test", entity.name)
        assertEquals("test", entity.slug)
        assertEquals("2024-01-01", entity.completedAt)
    }

    @Test
    fun `CompletedChallengeEntity maps to domain correctly`() {
        val entity = CompletedChallengeEntity(id = "abc", name = "Test", slug = "test", completedAt = "2024-01-01")
        val domain = entity.toDomain()
        assertEquals("abc", domain.id)
        assertEquals("Test", domain.name)
    }

    // ---- AuthoredChallenge ----

    @Test
    fun `AuthoredChallengeDataDto maps to domain correctly`() {
        val dto = AuthoredChallengeDataDto(
            id = "xyz", name = "My Kata", description = "Desc",
            rank = "-4", rankName = "4 kyu",
            tags = listOf("algorithms"), languages = listOf("kotlin")
        )
        val domain = dto.toDomain()
        assertEquals("xyz", domain.id)
        assertEquals("My Kata", domain.name)
        assertEquals(listOf("algorithms"), domain.tags)
    }

    // ---- ChallengeDetails ----

    @Test
    fun `ChallengeDetailsDto maps createdBy correctly`() {
        val dto = ChallengeDetailsDto(
            id = "1", name = "Kata", slug = "kata", category = "algorithms",
            publishedAt = null, approvedAt = null, languages = listOf("kotlin"),
            url = null, rank = RankDto("4", "4 kyu", "blue"), createdAt = "2020-01-01",
            createdBy = CreatedByDto("author", null),
            approvedBy = CreatedByDto("approver", null),
            description = "Do it", totalAttempts = 100, totalCompleted = 50,
            totalStars = 10, voteScore = 5, tags = listOf("fp"),
            contributorsWanted = false,
            unresolved = UnResolvedDto(issues = 2, suggestions = 3)
        )
        val domain = dto.toDomain()
        assertEquals("author", domain.createdBy?.username)
        assertEquals("approver", domain.approvedBy?.username)
        assertEquals(2, domain.unresolvedIssues)
        assertEquals("4 kyu", domain.rank?.name)
    }

    @Test
    fun `ChallengeDetailsDto maps null unresolved correctly`() {
        val dto = ChallengeDetailsDto(
            id = "1", name = "K", slug = "k", category = null,
            publishedAt = null, approvedAt = null, languages = null, url = null,
            rank = null, createdAt = null, createdBy = null, approvedBy = null,
            description = null, totalAttempts = null, totalCompleted = null,
            totalStars = null, voteScore = null, tags = null,
            contributorsWanted = false, unresolved = null
        )
        val domain = dto.toDomain()
        assertNull(domain.unresolvedIssues)
        assertNull(domain.rank)
        assertNull(domain.createdBy)
    }
}
