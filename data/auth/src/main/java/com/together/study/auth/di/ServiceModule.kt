package com.together.study.auth.di

import com.together.study.auth.service.AuthService
import com.together.study.remote.qualifier.NoAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(
        @NoAuth retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)
}