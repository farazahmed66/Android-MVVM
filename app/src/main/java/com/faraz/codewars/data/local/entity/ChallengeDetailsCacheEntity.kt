package com.faraz.codewars.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenge_details_cache")
data class ChallengeDetailsCacheEntity(
    @PrimaryKey val challengeId: String,
    val json: String,
    val cachedAt: Long = System.currentTimeMillis()
)