package com.faraz.codewars.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cache")
data class UserCacheEntity(
    @PrimaryKey val userName: String,
    val json: String,
    val cachedAt: Long = System.currentTimeMillis()
)