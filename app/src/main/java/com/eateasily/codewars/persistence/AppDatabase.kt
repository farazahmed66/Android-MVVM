package com.eateasily.codewars.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eateasily.codewars.models.Test

@Database(entities = [Test::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {

    abstract fun starWarsDao(): StarWarsDao
}