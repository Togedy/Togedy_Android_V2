package com.together.study.study.repository

import com.together.study.study.model.StudyBadge

interface StudyBadgeRepository {
    suspend fun getStudyBadges(): Result<List<StudyBadge>>

    suspend fun getLastCheckedBadgeId(): Long?
    suspend fun updateLastCheckedBadgeId(badgeId: Long)
}
