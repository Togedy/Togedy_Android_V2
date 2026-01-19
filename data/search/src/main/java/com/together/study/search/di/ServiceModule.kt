package com.together.study.search.di

import com.together.study.search.service.UnivScheduleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideUnivScheduleService(retrofit: Retrofit): UnivScheduleService =
        retrofit.create()
}