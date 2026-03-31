package com.together.study.planner.di

import com.together.study.planner.repository.PlannerRepository
import com.together.study.planner.repository.PlannerShareRepository
import com.together.study.planner.repositoryimpl.PlannerRepositoryImpl
import com.together.study.planner.repositoryimpl.PlannerShareRepositoryImpl
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
    abstract fun bindPlannerRepository(
        plannerRepositoryImpl: PlannerRepositoryImpl,
    ): PlannerRepository

    @Binds
    @Singleton
    abstract fun bindPlannerShareRepository(
        plannerShareRepositoryImpl: PlannerShareRepositoryImpl,
    ): PlannerShareRepository
}
