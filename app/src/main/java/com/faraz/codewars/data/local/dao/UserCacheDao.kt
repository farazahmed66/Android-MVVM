package com.faraz.codewars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faraz.codewars.data.local.entity.UserCacheEntity

@Dao
interface UserCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cache: UserCacheEntity)

    @Query("SELECT * FROM user_cache WHERE userName = :userName")
    suspend fun get(userName: String): UserCacheEntity?
}