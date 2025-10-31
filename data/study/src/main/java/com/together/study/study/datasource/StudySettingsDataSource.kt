package com.together.study.study.datasource

import com.together.study.study.service.StudySettingsService
import javax.inject.Inject

class StudySettingsDataSource @Inject constructor(
    private val studySettingsService: StudySettingsService,
) {
    suspend fun getStudyMembers(studyId: Long) = studySettingsService.getStudyMembers(studyId)
    suspend fun updateStudyMemberLimit(studyId: Long, studyMemberLimit: Int) =
        studySettingsService.updateStudyMemberLimit(studyId, studyMemberLimit)

    suspend fun deleteStudyAsLeader(studyId: Long) =
        studySettingsService.deleteStudyAsLeader(studyId)

    suspend fun deleteStudyAsMember(studyId: Long) =
        studySettingsService.deleteStudyAsMember(studyId)

    suspend fun deleteMemberFromStudy(studyId: Long, userId: Long) =
        studySettingsService.deleteMemberFromStudy(studyId, userId)

    suspend fun updateStudyLeader(studyId: Long, userId: Long) =
        studySettingsService.updateStudyLeader(studyId, userId)
}
