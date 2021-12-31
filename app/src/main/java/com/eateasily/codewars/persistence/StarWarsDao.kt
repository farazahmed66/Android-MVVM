package com.eateasily.codewars.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eateasily.codewars.models.UserChallengeData

@Dao
interface StarWarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleUsers(list: List<UserChallengeData>)

    @Query("SELECT * FROM users_table")
    fun getUsers(): PagingSource<Int, UserChallengeData>

    @Query("DELETE FROM users_table")
    suspend fun clearRepos()

    @Query("SELECT COUNT(id) from users_table")
    suspend fun count(): Int

}