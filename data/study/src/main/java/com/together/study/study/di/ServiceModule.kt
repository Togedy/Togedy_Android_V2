package com.together.study.study.di

import com.together.study.study.service.StudyDetailService
import com.together.study.study.service.StudyExploreService
import com.together.study.study.service.StudyMemberService
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

    @Provides
    @Singleton
    fun provideStudyDetailService(retrofit: Retrofit): StudyDetailService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideStudyMemberService(retrofit: Retrofit): StudyMemberService =
        retrofit.create()
}
