package com.faraz.codewars.di

import android.app.Application
import androidx.room.Room
import com.faraz.codewars.data.local.AppDatabase
import com.faraz.codewars.data.local.dao.AuthoredChallengeCacheDao
import com.faraz.codewars.data.local.dao.ChallengeDetailsCacheDao
import com.faraz.codewars.data.local.dao.CompletedChallengeDao
import com.faraz.codewars.data.local.dao.UserCacheDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "CodeWars.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCompletedChallengeDao(appDatabase: AppDatabase): CompletedChallengeDao {
        return appDatabase.completedChallengeDao()
    }

    @Provides
    @Singleton
    fun provideAuthoredChallengeCacheDao(appDatabase: AppDatabase): AuthoredChallengeCacheDao {
        return appDatabase.authoredChallengeCacheDao()
    }

    @Provides
    @Singleton
    fun provideChallengeDetailsCacheDao(appDatabase: AppDatabase): ChallengeDetailsCacheDao {
        return appDatabase.challengeDetailsCacheDao()
    }

    @Provides
    @Singleton
    fun provideUserCacheDao(appDatabase: AppDatabase): UserCacheDao {
        return appDatabase.userCacheDao()
    }
}