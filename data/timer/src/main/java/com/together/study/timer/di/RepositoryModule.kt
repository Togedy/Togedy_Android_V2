package com.together.study.timer.di

import com.together.study.timer.repository.TimerRepository
import com.together.study.timer.repositoryimpl.TimerRepositoryImpl
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
    abstract fun bindTimerRepository(
        timerRepositoryImpl: TimerRepositoryImpl,
    ): TimerRepository
}
