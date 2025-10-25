package com.together.study.study.datasource

import com.together.study.study.service.StudyMemberService
import javax.inject.Inject

class StudyMemberDataSource @Inject constructor(
    private val studyMemberService: StudyMemberService,
) {
    suspend fun getStudyMemberProfile(studyId: Long, userId: Long) =
        studyMemberService.getStudyMemberProfile(studyId, userId)

    suspend fun getStudyMemberTimeBlocks(studyId: Long, userId: Long) =
        studyMemberService.getStudyMemberTimeBlocks(studyId, userId)

    suspend fun getStudyMemberPlanner(studyId: Long, userId: Long) =
        studyMemberService.getStudyMemberPlanner(studyId, userId)

}
