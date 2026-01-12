package com.together.study.study.repository

import com.together.study.study.model.StudyMemberPlanner
import com.together.study.study.model.StudyMemberProfile
import com.together.study.study.model.StudyMemberTimeBlocks

interface StudyMemberRepository {
    suspend fun getStudyMemberProfile(studyId: Long, userId: Long): Result<StudyMemberProfile>
    suspend fun getStudyMemberTimeBlocks(studyId: Long, userId: Long): Result<StudyMemberTimeBlocks>
    suspend fun getStudyMemberPlanner(studyId: Long, userId: Long): Result<StudyMemberPlanner>
    suspend fun updatePlannerVisibility(
        studyId: Long,
        userId: Long,
        isVisible: Boolean,
    ): Result<Unit>
}
