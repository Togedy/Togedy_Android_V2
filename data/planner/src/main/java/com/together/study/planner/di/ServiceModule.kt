package com.together.study.planner.di

import com.together.study.planner.service.PlannerService
import com.together.study.planner.service.PlannerShareService
import com.together.study.planner.service.PlannerSubjectService
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
    fun providePlannerService(retrofit: Retrofit): PlannerService =
        retrofit.create()

    @Provides
    @Singleton
    fun providePlannerShareService(retrofit: Retrofit): PlannerShareService =
        retrofit.create()

    @Provides
    @Singleton
    fun providePlannerSubjectService(retrofit: Retrofit): PlannerSubjectService =
        retrofit.create()
}
