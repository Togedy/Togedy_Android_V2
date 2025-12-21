package com.together.study.study.repository

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
}

