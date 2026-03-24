package com.together.study.planner.datasource

import com.together.study.planner.dto.request.SubjectIndexRequest
import com.together.study.planner.dto.request.SubjectItemRequest
import com.together.study.planner.service.PlannerSubjectService
import javax.inject.Inject

class PlannerSubjectDataSource @Inject constructor(
    private val plannerSubjectService: PlannerSubjectService,
) {
    suspend fun getSubjects() =
        plannerSubjectService.getSubjects()

    suspend fun postSubject(request: SubjectItemRequest) =
        plannerSubjectService.postSubject(request)

    suspend fun patchSubject(request: SubjectItemRequest, subjectId: Long) =
        plannerSubjectService.patchSubject(request, subjectId)

    suspend fun deleteSubject(subjectId: Long) =
        plannerSubjectService.deleteSubject(subjectId)

    suspend fun moveSubjectIndex(request: SubjectIndexRequest, subjectId: Long) =
        plannerSubjectService.moveSubjectIndex(request, subjectId)
}
