package com.faraz.codewars.domain.model

data class ChallengeDetails(
    val id: String?,
    val name: String?,
    val slug: String?,
    val category: String?,
    val publishedAt: String?,
    val approvedAt: String?,
    val languages: List<String>?,
    val url: String?,
    val rank: ChallengeRank?,
    val createdAt: String?,
    val createdBy: UserRef?,
    val approvedBy: UserRef?,
    val description: String?,
    val totalAttempts: Long?,
    val totalCompleted: Long?,
    val totalStars: Int?,
    val voteScore: Int?,
    val tags: List<String>?,
    val contributorsWanted: Boolean,
    val unresolvedIssues: Int?,
    val unresolvedSuggestions: Int?
)

data class ChallengeRank(
    val id: String?,
    val name: String?,
    val color: String?
)

data class UserRef(
    val username: String?,
    val url: String?
)
