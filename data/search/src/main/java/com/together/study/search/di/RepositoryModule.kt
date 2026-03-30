package com.together.study.search.di

import com.together.study.search.repository.UnivScheduleRepository
import com.together.study.search.repositoryimpl.UnivScheduleRepositoryImpl
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
    abstract fun bindUnivSchedulesRepository(
        univScheduleRepositoryImpl: UnivScheduleRepositoryImpl,
    ): UnivScheduleRepository
}