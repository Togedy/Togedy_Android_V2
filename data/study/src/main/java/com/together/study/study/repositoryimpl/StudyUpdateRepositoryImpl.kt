package com.together.study.study.repositoryimpl

import androidx.core.net.toUri
import com.together.study.study.datasource.StudyUpdateDataSource
import com.together.study.study.mapper.toDomain
import com.together.study.study.repository.StudyUpdateRepository
import com.together.study.util.getHttpExceptionMessage
import javax.inject.Inject

class StudyUpdateRepositoryImpl @Inject constructor(
    private val studyUpdateDataSource: StudyUpdateDataSource,
) : StudyUpdateRepository {
    override suspend fun createStudy(
        challengeGoalTime: Int?,
        studyName: String,
        studyDescription: String?,
        studyMemberLimit: Int,
        studyTag: String,
        studyPassword: String?,
        studyImageUri: String?,
    ): Result<Unit> = runCatching<Unit> {
        val uri = studyImageUri?.toUri()
        studyUpdateDataSource.createStudy(
            challengeGoalTime = challengeGoalTime,
            studyName = studyName,
            studyDescription = studyDescription,
            studyMemberLimit = studyMemberLimit,
            studyTag = studyTag,
            studyPassword = studyPassword,
            studyImageUri = uri,
        )
    }.recoverCatching { throwable ->
        val serverMessage = throwable.getHttpExceptionMessage()
        throw Exception(serverMessage ?: throwable.message ?: "스터디 생성에 실패했습니다.")
    }

    override suspend fun checkStudyNameDuplicate(name: String) =
        runCatching {
            val response = studyUpdateDataSource.checkStudyNameDuplicate(name).response
            response.toDomain()
        }

    override suspend fun updateStudy(
        studyId: Long,
        challengeGoalTime: Int?,
        studyName: String,
        studyDescription: String?,
        studyMemberLimit: Int,
        studyTag: String,
        studyPassword: String?,
        studyImageUri: String?,
    ): Result<Unit> = runCatching<Unit> {
        val uri = studyImageUri?.toUri()
        studyUpdateDataSource.updateStudy(
            studyId = studyId,
            challengeGoalTime = challengeGoalTime,
            studyName = studyName,
            studyDescription = studyDescription,
            studyMemberLimit = studyMemberLimit,
            studyTag = studyTag,
            studyPassword = studyPassword,
            studyImageUri = uri,
        )
    }.recoverCatching { throwable ->
        val serverMessage = throwable.getHttpExceptionMessage()
        throw Exception(serverMessage ?: throwable.message ?: "스터디 수정에 실패했습니다.")
    }
}

