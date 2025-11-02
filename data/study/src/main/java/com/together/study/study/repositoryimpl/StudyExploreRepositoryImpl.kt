package com.together.study.study.repositoryimpl

import com.together.study.study.datasource.StudyExploreDataSource
import com.together.study.study.mapper.toDomain
import com.together.study.study.mapper.toExploreStudyItem
import com.together.study.study.model.ExploreStudyFilter
import com.together.study.study.model.ExploreStudyInfo
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.model.MyStudyInfo
import com.together.study.study.repository.StudyExploreRepository
import javax.inject.Inject

class StudyExploreRepositoryImpl @Inject constructor(
    private val studyExploreDataSource: StudyExploreDataSource,
) : StudyExploreRepository {
    override suspend fun getMyStudyInfo(): Result<MyStudyInfo> =
        runCatching {
            val response = studyExploreDataSource.getMyStudyInfo().response
            response.toDomain()
        }

    override suspend fun getExploreStudyItems(request: ExploreStudyFilter): Result<ExploreStudyInfo> =
        runCatching {
            val response = studyExploreDataSource.getExploreStudyItems(
                tag = request.tag,
                filter = request.filter,
                joinable = request.joinable,
                challenge = request.challenge,
                page = request.page,
                size = request.size,
            ).response
            response.toDomain()
        }

    override suspend fun getPopularStudyItems(): Result<List<ExploreStudyItem>> =
        runCatching {
            val response = studyExploreDataSource.getPopularStudyItems().response
            response.map { it.toExploreStudyItem() }
        }

}
