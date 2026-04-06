package com.faraz.codewars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faraz.codewars.data.local.entity.AuthoredChallengeCacheEntity

@Dao
interface AuthoredChallengeCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cache: AuthoredChallengeCacheEntity)

    @Query("SELECT * FROM authored_challenges_cache WHERE userName = :userName")
    suspend fun get(userName: String): AuthoredChallengeCacheEntity?
}