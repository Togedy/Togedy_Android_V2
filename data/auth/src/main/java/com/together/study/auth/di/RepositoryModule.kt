package com.together.study.auth.di

import com.together.study.auth.repository.AuthRepository
import com.together.study.auth.repository.TokenRepository
import com.together.study.auth.repositoryImpl.AuthRepositoryImpl
import com.together.study.auth.repositoryImpl.TokenRepositoryImpl
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
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        impl: TokenRepositoryImpl
    ): TokenRepository
}