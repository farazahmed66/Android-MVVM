package com.eateasily.codewars.domain.model

data class AuthoredChallengeData(
    val id: String?,
    val name: String?,
    val description: String?,
    val rank: String?,
    val rankName: String?,
    val tags: List<String>?,
    val languages: List<String>?
)
