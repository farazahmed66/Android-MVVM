package com.faraz.codewars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faraz.codewars.data.local.entity.ChallengeDetailsCacheEntity

@Dao
interface ChallengeDetailsCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cache: ChallengeDetailsCacheEntity)

    @Query("SELECT * FROM challenge_details_cache WHERE challengeId = :challengeId")
    suspend fun get(challengeId: String): ChallengeDetailsCacheEntity?
}