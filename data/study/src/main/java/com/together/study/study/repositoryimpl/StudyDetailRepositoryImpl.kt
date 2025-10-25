package com.together.study.study.repositoryimpl

import com.together.study.study.datasource.StudyDetailDataSource
import com.together.study.study.mapper.toDomain
import com.together.study.study.model.StudyAttendance
import com.together.study.study.model.StudyDetailInfo
import com.together.study.study.model.StudyMember
import com.together.study.study.repository.StudyDetailRepository
import javax.inject.Inject

class StudyDetailRepositoryImpl @Inject constructor(
    private val studyDetailDataSource: StudyDetailDataSource,
) : StudyDetailRepository {
    override suspend fun getStudyDetailInfo(studyId: Long): Result<StudyDetailInfo> =
        runCatching {
            val response = studyDetailDataSource.getStudyDetailInfo(studyId).response
            response.toDomain()
        }

    override suspend fun getStudyMembers(studyId: Long): Result<List<StudyMember>> =
        runCatching {
            val response = studyDetailDataSource.getStudyMembers(studyId).response
            response.toDomain()
        }

    override suspend fun getStudyAttendance(
        studyId: Long,
        startDate: String,
        endDate: String,
    ): Result<List<StudyAttendance>> =
        runCatching {
            val response =
                studyDetailDataSource.getStudyAttendance(studyId, startDate, endDate).response
            response.toDomain()
        }

}
