package com.together.study.calendar.di

import com.together.study.calendar.repository.CalendarRepository
import com.together.study.calendar.repository.UserScheduleRepository
import com.together.study.calendar.repositoryimpl.CalendarRepositoryImpl
import com.together.study.calendar.repositoryimpl.UserScheduleRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindUserScheduleRepository(
        userScheduleRepositoryImpl: UserScheduleRepositoryImpl,
    ): UserScheduleRepository
}
