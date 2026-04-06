package com.eateasily.codewars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eateasily.codewars.data.local.entity.ChallengeDetailsCacheEntity

@Dao
interface ChallengeDetailsCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cache: ChallengeDetailsCacheEntity)

    @Query("SELECT * FROM challenge_details_cache WHERE challengeId = :challengeId")
    suspend fun get(challengeId: String): ChallengeDetailsCacheEntity?
}