package com.together.study.study.repository

import com.together.study.study.model.StudyMemberBriefInfo

interface StudySettingsRepository {
    suspend fun getStudyMembers(studyId: Long): Result<List<StudyMemberBriefInfo>>
    suspend fun updateStudyMemberLimit(studyId: Long, studyMemberLimit: Int): Result<Unit>

    suspend fun deleteStudyAsLeader(studyId: Long): Result<Unit>
    suspend fun deleteStudyAsMember(studyId: Long): Result<Unit>
    suspend fun deleteMemberFromStudy(studyId: Long, userId: Long): Result<Unit>

    suspend fun updateStudyLeader(studyId: Long, userId: Long): Result<Unit>
}
