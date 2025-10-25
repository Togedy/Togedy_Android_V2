package com.together.study.study.di

import com.together.study.study.repository.StudyExploreRepository
import com.together.study.study.repositoryimpl.StudyExploreRepositoryImpl
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
    abstract fun bindStudyExploreRepository(
        studyExploreRepositoryImpl: StudyExploreRepositoryImpl,
    ): StudyExploreRepository
}
