package com.eateasily.codewars.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authored_challenges_cache")
data class AuthoredChallengeCacheEntity(
    @PrimaryKey val userName: String,
    val json: String,
    val cachedAt: Long = System.currentTimeMillis()
)