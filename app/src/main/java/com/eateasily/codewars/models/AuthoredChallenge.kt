package com.eateasily.codewars.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AuthoredChallenge(
    @field:Json(name = "data") val authoredChallengeData: List<AuthoredChallengeData>,
) {
}

@JsonClass(generateAdapter = true)
data class AuthoredChallengeData(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "rank") val rank: String?,
    @field:Json(name = "rankName") val rankName: String?,
    @field:Json(name = "tags") val tags: List<String>?,
    @field:Json(name = "languages") val languages: List<String>?
) {
}
