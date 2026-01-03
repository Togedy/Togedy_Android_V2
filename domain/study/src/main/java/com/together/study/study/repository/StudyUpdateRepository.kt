package com.together.study.study.repository

import com.together.study.study.model.StudyNameDuplicate

interface StudyUpdateRepository {
    suspend fun createStudy(
        challengeGoalTime: Int?,
        studyName: String,
        studyDescription: String?,
        studyMemberLimit: Int,
        studyTag: String,
        studyPassword: String?,
        studyImageUri: String?,
    ): Result<Unit>

    suspend fun checkStudyNameDuplicate(name: String): Result<StudyNameDuplicate>

    suspend fun updateStudy(
        studyId: Long,
        challengeGoalTime: Int?,
        studyName: String,
        studyDescription: String?,
        studyMemberLimit: Int,
        studyTag: String,
        studyPassword: String?,
        studyImageUri: String?,
    ): Result<Unit>
}

