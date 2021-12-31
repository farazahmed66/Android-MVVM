package com.eateasily.codewars.di

import android.app.Application
import androidx.room.Room
import com.eateasily.codewars.persistence.AppDatabase
import com.eateasily.codewars.persistence.StarWarsDao
import com.eateasily.codewars.persistence.TypeResponseConvertor
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
    fun provideAppDatabase(
        application: Application,
//        typeResponseConverter: TypeResponseConvertor
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "StarWars.db")
            .fallbackToDestructiveMigration()
//            .addTypeConverter(typeResponseConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideStarWarsDao(appDatabase: AppDatabase): StarWarsDao {
        return appDatabase.starWarsDao()
    }
}