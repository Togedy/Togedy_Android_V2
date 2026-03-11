package com.together.study.auth.di

import com.together.study.auth.repository.AuthRepository
import com.together.study.auth.repositoryImpl.AuthRepositoryImpl
import dagger.Binds
import javax.inject.Singleton

abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}