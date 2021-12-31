package com.eateasily.codewars.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@JsonClass(generateAdapter = true)
data class UserChallenge(
    @field:Json(name = "totalPages") val totalPages: Int,
    @field:Json(name = "totalItems") val totalItems: Long?,
    @field:Json(name = "data") val data: List<UserChallengeData>?,
) {
}

@Entity(tableName = "users_table")
@JsonClass(generateAdapter = true)
data class UserChallengeData(
    @PrimaryKey(autoGenerate = false)
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "slug") val slug: String?,
    @field:Json(name = "completedAt") val completedAt: String?,
//    @field:Json(name = "completedLanguages") val completedLanguages: List<String>?,
) {
}