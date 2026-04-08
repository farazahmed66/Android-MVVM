package com.faraz.codewars.domain.model

data class User(
    val userName: String?,
    val name: String?,
    val honor: Int?,
    val clan: String?,
    val leaderboardPosition: Int?,
    val skills: List<String>?,
    val overallRank: RankInfo,
    val languageRanks: Map<String, RankInfo>,
    val totalAuthored: Int?,
    val totalCompleted: Int?
)

data class RankInfo(
    val rank: Int,
    val name: String,
    val color: String,
    val score: Long
)
