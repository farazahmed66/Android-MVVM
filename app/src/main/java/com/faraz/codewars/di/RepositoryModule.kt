package com.faraz.codewars.di

import com.faraz.codewars.data.repository.ChallengeRepositoryImpl
import com.faraz.codewars.data.repository.UserRepositoryImpl
import com.faraz.codewars.domain.repository.ChallengeRepository
import com.faraz.codewars.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindChallengeRepository(impl: ChallengeRepositoryImpl): ChallengeRepository
}