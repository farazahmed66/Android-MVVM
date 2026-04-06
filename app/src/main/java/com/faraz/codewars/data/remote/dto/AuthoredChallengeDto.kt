package com.faraz.codewars.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthoredChallengeDto(
    @field:Json(name = "data") val data: List<AuthoredChallengeDataDto>
)

@JsonClass(generateAdapter = true)
data class AuthoredChallengeDataDto(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "rank") val rank: String?,
    @field:Json(name = "rankName") val rankName: String?,
    @field:Json(name = "tags") val tags: List<String>?,
    @field:Json(name = "languages") val languages: List<String>?
)
