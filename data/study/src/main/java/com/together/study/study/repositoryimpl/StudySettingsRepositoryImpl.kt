package com.together.study.study.repositoryimpl

import com.together.study.study.datasource.StudySettingsDataSource
import com.together.study.study.mapper.toDomain
import com.together.study.study.model.StudyMemberBriefInfo
import com.together.study.study.repository.StudySettingsRepository
import javax.inject.Inject

class StudySettingsRepositoryImpl @Inject constructor(
    private val studySettingsDataSource: StudySettingsDataSource,
) : StudySettingsRepository {
    override suspend fun getStudyMembers(studyId: Long): Result<List<StudyMemberBriefInfo>> =
        runCatching {
            val response = studySettingsDataSource.getStudyMembers(studyId).response
            response.toDomain()
        }

    override suspend fun updateStudyMemberLimit(
        studyId: Long,
        studyMemberLimit: Int,
    ): Result<Unit> =
        runCatching {
            studySettingsDataSource.updateStudyMemberLimit(studyId, studyMemberLimit)
        }.map { }

    override suspend fun deleteStudyAsLeader(studyId: Long): Result<Unit> =
        runCatching {
            studySettingsDataSource.deleteStudyAsLeader(studyId)
        }.map { }

    override suspend fun deleteStudyAsMember(studyId: Long): Result<Unit> =
        runCatching {
            studySettingsDataSource.deleteStudyAsMember(studyId)
        }.map { }

    override suspend fun deleteMemberFromStudy(
        studyId: Long,
        userId: Long,
    ): Result<Unit> =
        runCatching {
            studySettingsDataSource.deleteMemberFromStudy(studyId, userId)
        }.map { }

    override suspend fun updateStudyLeader(
        studyId: Long,
        userId: Long,
    ): Result<Unit> =
        runCatching {
            studySettingsDataSource.updateStudyLeader(studyId, userId)
        }.map { }
}