package com.eateasily.codewars.data.mapper

import com.eateasily.codewars.data.local.entity.CompletedChallengeEntity
import com.eateasily.codewars.data.remote.dto.AuthoredChallengeDataDto
import com.eateasily.codewars.data.remote.dto.ChallengeDetailsDto
import com.eateasily.codewars.data.remote.dto.CompletedChallengeDto
import com.eateasily.codewars.domain.model.AuthoredChallengeData
import com.eateasily.codewars.domain.model.ChallengeDetails
import com.eateasily.codewars.domain.model.ChallengeRank
import com.eateasily.codewars.domain.model.CompletedChallenge
import com.eateasily.codewars.domain.model.UserRef

fun CompletedChallengeDto.toEntity(): CompletedChallengeEntity =
    CompletedChallengeEntity(id = id, name = name, slug = slug, completedAt = completedAt)

fun CompletedChallengeEntity.toDomain(): CompletedChallenge =
    CompletedChallenge(id = id, name = name, slug = slug, completedAt = completedAt)

fun AuthoredChallengeDataDto.toDomain(): AuthoredChallengeData = AuthoredChallengeData(
    id = id,
    name = name,
    description = description,
    rank = rank,
    rankName = rankName,
    tags = tags,
    languages = languages
)

fun ChallengeDetailsDto.toDomain(): ChallengeDetails = ChallengeDetails(
    id = id,
    name = name,
    slug = slug,
    category = category,
    publishedAt = publishedAt,
    approvedAt = approvedAt,
    languages = languages,
    url = url,
    rank = rank?.let { ChallengeRank(it.id, it.name, it.color) },
    createdAt = createdAt,
    createdBy = createdBy?.let { UserRef(it.username, it.url) },
    approvedBy = approvedBy?.let { UserRef(it.username, it.url) },
    description = description,
    totalAttempts = totalAttempts,
    totalCompleted = totalCompleted,
    totalStars = totalStars,
    voteScore = voteScore,
    tags = tags,
    contributorsWanted = contributorsWanted,
    unresolvedIssues = unresolved?.issues,
    unresolvedSuggestions = unresolved?.suggestions
)
