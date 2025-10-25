package com.together.study.study.repository

import com.together.study.study.model.StudyAttendance
import com.together.study.study.model.StudyDetailInfo
import com.together.study.study.model.StudyMember

interface StudyDetailRepository {
    suspend fun getStudyDetailInfo(studyId: Long): Result<StudyDetailInfo>
    suspend fun getStudyMembers(studyId: Long): Result<List<StudyMember>>
    suspend fun getStudyAttendance(
        studyId: Long,
        startDate: String,
        endDate: String,
    ): Result<List<StudyAttendance>>
}
