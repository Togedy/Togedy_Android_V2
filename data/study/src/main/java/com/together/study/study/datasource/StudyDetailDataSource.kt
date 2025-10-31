package com.together.study.study.datasource

import com.together.study.study.dto.JoinStudyRequest
import com.together.study.study.service.StudyDetailService
import javax.inject.Inject

class StudyDetailDataSource @Inject constructor(
    private val studyDetailService: StudyDetailService,
) {
    suspend fun getStudyDetailInfo(studyId: Long) = studyDetailService.getStudyDetailInfo(studyId)

    suspend fun getStudyMembers(studyId: Long) = studyDetailService.getStudyMembers(studyId)

    suspend fun getStudyAttendance(studyId: Long, startDate: String, endDate: String) =
        studyDetailService.getStudyAttendance(
            studyId = studyId,
            startDate = startDate,
            endDate = endDate
        )

    suspend fun postStudyJoin(studyId: Long, password: String?) =
        studyDetailService.postStudyJoin(
            studyId = studyId,
            body = JoinStudyRequest(password)
        )

}