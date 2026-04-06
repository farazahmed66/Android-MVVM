package com.eateasily.codewars.data.mapper

import com.eateasily.codewars.data.remote.dto.OverallDto
import com.eateasily.codewars.data.remote.dto.UserDto
import com.eateasily.codewars.domain.model.RankInfo
import com.eateasily.codewars.domain.model.User

fun UserDto.toDomain(): User = User(
    userName = userName,
    name = name,
    honor = honor,
    clan = clan,
    leaderboardPosition = leaderboardPosition,
    skills = skills,
    overallRank = ranks.overall.toDomain(),
    languageRanks = ranks.languages.mapValues { it.value.toDomain() },
    codeChallengesAuthored = codeChallenges?.totalAuthored
)

fun OverallDto.toDomain(): RankInfo = RankInfo(
    rank = rank,
    name = name,
    color = color,
    score = score
)