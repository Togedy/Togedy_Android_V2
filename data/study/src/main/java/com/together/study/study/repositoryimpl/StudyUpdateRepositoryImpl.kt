package com.together.study.study.repositoryimpl

import androidx.core.net.toUri
import com.together.study.study.datasource.StudyUpdateDataSource
import com.together.study.study.mapper.toDomain
import com.together.study.study.repository.StudyUpdateRepository
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
    ): Result<Unit> = runCatching {
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
    }

    override suspend fun checkStudyNameDuplicate(name: String) =
        runCatching {
            val response = studyUpdateDataSource.checkStudyNameDuplicate(name).response
            response.toDomain()
        }
}

