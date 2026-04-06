package com.eateasily.codewars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eateasily.codewars.data.local.dao.AuthoredChallengeCacheDao
import com.eateasily.codewars.data.local.dao.ChallengeDetailsCacheDao
import com.eateasily.codewars.data.local.dao.CompletedChallengeDao
import com.eateasily.codewars.data.local.dao.RemoteKeysDao
import com.eateasily.codewars.data.local.dao.UserCacheDao
import com.eateasily.codewars.data.local.entity.AuthoredChallengeCacheEntity
import com.eateasily.codewars.data.local.entity.ChallengeDetailsCacheEntity
import com.eateasily.codewars.data.local.entity.CompletedChallengeEntity
import com.eateasily.codewars.data.local.entity.RemoteKeysEntity
import com.eateasily.codewars.data.local.entity.UserCacheEntity

@Database(
    entities = [
        RemoteKeysEntity::class,
        CompletedChallengeEntity::class,
        AuthoredChallengeCacheEntity::class,
        ChallengeDetailsCacheEntity::class,
        UserCacheEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun completedChallengeDao(): CompletedChallengeDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun authoredChallengeCacheDao(): AuthoredChallengeCacheDao
    abstract fun challengeDetailsCacheDao(): ChallengeDetailsCacheDao
    abstract fun userCacheDao(): UserCacheDao
}