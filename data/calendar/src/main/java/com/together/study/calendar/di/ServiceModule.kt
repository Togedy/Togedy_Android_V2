package com.together.study.calendar.di

import com.together.study.calendar.service.CalendarService
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
    fun provideCalendarService(retrofit: Retrofit): CalendarService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideUserScheduleService(retrofit: Retrofit): UserScheduleService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService =
        retrofit.create()
}
