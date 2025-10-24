package com.together.study.study.repository

import com.together.study.study.model.ExploreStudyFilter
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.model.MyStudyInfo

interface StudyExploreRepository {
    suspend fun getMyStudyInfo(): Result<MyStudyInfo>
    suspend fun getExploreStudyItems(request: ExploreStudyFilter): Result<Pair<Boolean, List<ExploreStudyItem>>>
}
