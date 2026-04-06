package com.eateasily.codewars.domain.model

data class User(
    val userName: String?,
    val name: String?,
    val honor: String?,
    val clan: String?,
    val leaderboardPosition: Int?,
    val skills: List<String>?,
    val overallRank: RankInfo,
    val languageRanks: Map<String, RankInfo>,
    val codeChallengesAuthored: Long?
)

data class RankInfo(
    val rank: Int,
    val name: String,
    val color: String,
    val score: Long
)
