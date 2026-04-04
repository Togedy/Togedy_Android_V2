package com.together.study.planner.repositoryimpl

import com.together.study.planner.datasource.PlannerSubjectDataSource
import com.together.study.planner.dto.request.SubjectIndexRequest
import com.together.study.planner.dto.request.SubjectItemRequest
import com.together.study.planner.mapper.toDomain
import com.together.study.planner.model.SubjectItem
import com.together.study.planner.repository.PlannerSubjectRepository
import javax.inject.Inject

class PlannerSubjectRepositoryImpl @Inject constructor(
    private val dataSource: PlannerSubjectDataSource,
) : PlannerSubjectRepository {
    override suspend fun getSubjects(): Result<List<SubjectItem>> =
        runCatching {
            val response = dataSource.getSubjects().response
            response.map { it.toDomain() }
        }

    override suspend fun postSubject(
        name: String,
        color: String,
    ): Result<Unit> = runCatching {
        dataSource.postSubject(
            request = SubjectItemRequest(
                subjectName = name,
                subjectColor = color,
            )
        )
    }

    override suspend fun patchSubject(
        id: Long,
        name: String?,
        color: String?,
    ): Result<Unit> = runCatching {
        dataSource.patchSubject(
            request = SubjectItemRequest(
                subjectName = name,
                subjectColor = color
            ),
            subjectId = id,
        )
    }

    override suspend fun deleteSubject(id: Long): Result<Unit> =
        runCatching { dataSource.deleteSubject(subjectId = id) }

    override suspend fun moveSubjectIndex(
        id: Long,
        prevId: Long?,
        nextId: Long?,
    ): Result<Unit> = runCatching{
        dataSource.moveSubjectIndex(
            request = SubjectIndexRequest(
                prevId = prevId,
                nextId = nextId,
            ),
            subjectId = id,
        )
    }
}
