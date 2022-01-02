package com.eateasily.codewars.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeDetails(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "slug") val slug: String?,
    @field:Json(name = "category") val category: String?,
    @field:Json(name = "publishedAt") val publishedAt: String?,
    @field:Json(name = "approvedAt") val approvedAt: String?,
    @field:Json(name = "languages") val languages: List<String>?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "rank") val rank: Rank?,
    @field:Json(name = "createdAt") val createdAt: String?,
    @field:Json(name = "createdBy") val createdBy: CreatedBy?,
    @field:Json(name = "approvedBy") val approvedBy: CreatedBy?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "totalAttempts") val totalAttempts: Long?,
    @field:Json(name = "totalCompleted") val totalCompleted: Long?,
    @field:Json(name = "totalStars") val totalStars: Int?,
    @field:Json(name = "voteScore") val voteScore: Int?,
    @field:Json(name = "tags") val tags: List<String>?,
    @field:Json(name = "contributorsWanted") val contributorsWanted: Boolean,
    @field:Json(name = "unresolved") val unresolved: UnResolved?,
    @field:Json(name = "Success", ignore = true) var isSuccess: Boolean = true,
    @field:Json(name = "reason", ignore = true) val reason: String = ""
)

@JsonClass(generateAdapter = true)
data class Rank(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "color") val color: String?,
)

@JsonClass(generateAdapter = true)
data class CreatedBy(
    @field:Json(name = "username") val username: String?,
    @field:Json(name = "url") val url: String?,
)

@JsonClass(generateAdapter = true)
data class UnResolved(
    @field:Json(name = "issues") val issues: Int?,
    @field:Json(name = "suggestions") val suggestions: Int?

)
