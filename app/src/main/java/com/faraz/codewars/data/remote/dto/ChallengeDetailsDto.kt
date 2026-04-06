package com.faraz.codewars.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeDetailsDto(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "slug") val slug: String?,
    @field:Json(name = "category") val category: String?,
    @field:Json(name = "publishedAt") val publishedAt: String?,
    @field:Json(name = "approvedAt") val approvedAt: String?,
    @field:Json(name = "languages") val languages: List<String>?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "rank") val rank: RankDto?,
    @field:Json(name = "createdAt") val createdAt: String?,
    @field:Json(name = "createdBy") val createdBy: CreatedByDto?,
    @field:Json(name = "approvedBy") val approvedBy: CreatedByDto?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "totalAttempts") val totalAttempts: Long?,
    @field:Json(name = "totalCompleted") val totalCompleted: Long?,
    @field:Json(name = "totalStars") val totalStars: Int?,
    @field:Json(name = "voteScore") val voteScore: Int?,
    @field:Json(name = "tags") val tags: List<String>?,
    @field:Json(name = "contributorsWanted") val contributorsWanted: Boolean,
    @field:Json(name = "unresolved") val unresolved: UnResolvedDto?,
    @field:Json(name = "Success", ignore = true) var isSuccess: Boolean = true,
    @field:Json(name = "reason", ignore = true) val reason: String = ""
)

@JsonClass(generateAdapter = true)
data class RankDto(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "color") val color: String?
)

@JsonClass(generateAdapter = true)
data class CreatedByDto(
    @field:Json(name = "username") val username: String?,
    @field:Json(name = "url") val url: String?
)

@JsonClass(generateAdapter = true)
data class UnResolvedDto(
    @field:Json(name = "issues") val issues: Int?,
    @field:Json(name = "suggestions") val suggestions: Int?
)