package com.together.study.study.di

import com.together.study.study.repository.StudyDetailRepository
import com.together.study.study.repository.StudyExploreRepository
import com.together.study.study.repository.StudyMemberRepository
import com.together.study.study.repository.StudySettingsRepository
import com.together.study.study.repository.StudyUpdateRepository
import com.together.study.study.repositoryimpl.StudyDetailRepositoryImpl
import com.together.study.study.repositoryimpl.StudyExploreRepositoryImpl
import com.together.study.study.repositoryimpl.StudyMemberRepositoryImpl
import com.together.study.study.repositoryimpl.StudySettingsRepositoryImpl
import com.together.study.study.repositoryimpl.StudyUpdateRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindStudyDetailRepository(
        studyDetailRepositoryImpl: StudyDetailRepositoryImpl,
    ): StudyDetailRepository

    @Binds
    @Singleton
    abstract fun bindStudyMemberRepository(
        studyMemberRepositoryImpl: StudyMemberRepositoryImpl,
    ): StudyMemberRepository

    @Binds
    @Singleton
    abstract fun bindStudySettingsRepository(
        studySettingsRepositoryImpl: StudySettingsRepositoryImpl,
    ): StudySettingsRepository

    @Binds
    @Singleton
    abstract fun bindStudyUpdateRepository(
        studyUpdateRepositoryImpl: StudyUpdateRepositoryImpl,
    ): StudyUpdateRepository
}
