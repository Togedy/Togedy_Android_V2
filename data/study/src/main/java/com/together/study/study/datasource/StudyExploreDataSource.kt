package com.together.study.study.datasource

import com.together.study.study.service.StudyExploreService
import javax.inject.Inject

class StudyExploreDataSource @Inject constructor(
    private val studyExploreService: StudyExploreService,
) {
    suspend fun getMyStudyInfo() = studyExploreService.getMyStudyInfo()

    suspend fun getExploreStudyItems(
        tag: List<String>?,
        filter: String,
        joinable: Boolean,
        challenge: Boolean,
        page: Int?,
        size: Int?,
    ) = studyExploreService.getExploreStudyItems(
        tag = tag,
        filter = filter,
        joinable = joinable,
        challenge = challenge,
        page = page,
        size = size,
    )

    suspend fun getPopularStudyItems() = studyExploreService.getPopularStudyItems()
}
