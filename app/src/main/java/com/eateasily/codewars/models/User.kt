package com.eateasily.codewars.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "username") val userName: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "honor") val honor: String,
    @field:Json(name = "clan") val clan: String,
    @field:Json(name = "leaderboardPosition") val leaderboardPosition: Int,
    @field:Json(name = "skills") val skills: List<String>,
    @field:Json(name = "ranks") val ranks: Ranks,
    @field:Json(name = "codeChallenges") val codeChallenges: CodeChallenges,
    @field:Json(name = "Success", ignore = true) var isSuccess: Boolean = true,
    @field:Json(name = "reason", ignore = true) val reason: String = ""
) {
}

@JsonClass(generateAdapter = true)
data class Ranks(
    @field:Json(name = "overall") val overall: Overall,
    @field:Json(name = "languages") val languages: Map<String, Overall>
)

@JsonClass(generateAdapter = true)
data class Languages(
    @field:Json(name = "rank") val rank: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "color") val color: String,
    @field:Json(name = "score") val score: Long,
)

@JsonClass(generateAdapter = true)
data class Overall(
    @field:Json(name = "rank") val rank: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "color") val color: String,
    @field:Json(name = "score") val score: Long,
)

@JsonClass(generateAdapter = true)
data class CodeChallenges(
    @field:Json(name = "totalAuthored") val totalAuthored: Long,
)