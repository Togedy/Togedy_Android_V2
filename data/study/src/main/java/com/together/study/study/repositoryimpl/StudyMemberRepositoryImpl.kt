package com.together.study.study.repositoryimpl

import com.together.study.study.datasource.StudyMemberDataSource
import com.together.study.study.mapper.toDomain
import com.together.study.study.model.StudyMemberPlanner
import com.together.study.study.model.StudyMemberProfile
import com.together.study.study.model.StudyMemberTimeBlocks
import com.together.study.study.repository.StudyMemberRepository
import javax.inject.Inject

class StudyMemberRepositoryImpl @Inject constructor(
    private val studyMemberDataSource: StudyMemberDataSource,
) : StudyMemberRepository {
    override suspend fun getStudyMemberProfile(
        studyId: Long,
        userId: Long,
    ): Result<StudyMemberProfile> =
        runCatching {
            val response = studyMemberDataSource.getStudyMemberProfile(studyId, userId).response
            response.toDomain()
        }

    override suspend fun getStudyMemberTimeBlocks(
        studyId: Long,
        userId: Long,
    ): Result<StudyMemberTimeBlocks> =
        runCatching {
            val response = studyMemberDataSource.getStudyMemberTimeBlocks(studyId, userId).response
            response.toDomain()
        }

    override suspend fun getStudyMemberPlanner(
        studyId: Long,
        userId: Long,
    ): Result<StudyMemberPlanner> =
        runCatching {
            val response = studyMemberDataSource.getStudyMemberPlanner(studyId, userId).response
            response.toDomain()
        }

    override suspend fun postPlannerVisibility(
        studyId: Long,
        userId: Long,
        isVisible: Boolean,
    ): Result<Unit> =
        runCatching {
            studyMemberDataSource.postPlannerVisibility(studyId, userId, isVisible)
        }.map { }
}
