package com.together.study.planner.repository

import com.together.study.planner.model.SubjectItem

interface PlannerSubjectRepository {
    suspend fun getSubjects(): Result<List<SubjectItem>>
    suspend fun postSubject(name: String, color: String): Result<Unit>
    suspend fun patchSubject(id: Long, name: String?, color: String?): Result<Unit>
    suspend fun deleteSubject(id: Long): Result<Unit>
    suspend fun moveSubjectIndex(id: Long, prevId: Long?, nextId: Long?): Result<Unit>
}
