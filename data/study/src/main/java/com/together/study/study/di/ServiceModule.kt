package com.together.study.study.di

import com.together.study.study.service.StudyExploreService
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
    fun provideStudyExploreService(retrofit: Retrofit): StudyExploreService =
        retrofit.create()
}
