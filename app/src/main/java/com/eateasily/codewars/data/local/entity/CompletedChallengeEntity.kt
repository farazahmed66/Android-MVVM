package com.eateasily.codewars.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class CompletedChallengeEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String?,
    val slug: String?,
    val completedAt: String?
)