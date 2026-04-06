package com.eateasily.codewars.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eateasily.codewars.data.local.entity.CompletedChallengeEntity

@Dao
interface CompletedChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CompletedChallengeEntity>)

    @Query("SELECT * FROM users_table ORDER BY completedAt DESC")
    fun getAll(): PagingSource<Int, CompletedChallengeEntity>

    @Query("DELETE FROM users_table")
    suspend fun clearAll()

    @Query("SELECT COUNT(id) from users_table")
    suspend fun count(): Int
}