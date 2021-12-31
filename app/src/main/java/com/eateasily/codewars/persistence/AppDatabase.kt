package com.eateasily.codewars.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eateasily.codewars.entity.RemoteKeys
import com.eateasily.codewars.models.UserChallengeData

@Database(
    entities = [RemoteKeys::class, UserChallengeData::class],
    version = 1000,
    exportSchema = true
)
//@TypeConverters(value = [TypeResponseConvertor::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun starWarsDao(): StarWarsDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}