package com.together.study.calendar.di

import com.together.study.calendar.repository.CalendarRepository
import com.together.study.calendar.repositoryimpl.CalendarRepositoryImpl
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
    abstract fun bindCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl,
    ): CalendarRepository
}