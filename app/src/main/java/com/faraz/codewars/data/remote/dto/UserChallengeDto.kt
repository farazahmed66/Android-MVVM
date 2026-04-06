package com.faraz.codewars.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserChallengeDto(
    @field:Json(name = "totalPages") val totalPages: Int,
    @field:Json(name = "totalItems") val totalItems: Long?,
    @field:Json(name = "data") val data: List<CompletedChallengeDto>?
)

@JsonClass(generateAdapter = true)
data class CompletedChallengeDto(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "slug") val slug: String?,
    @field:Json(name = "completedAt") val completedAt: String?
)
