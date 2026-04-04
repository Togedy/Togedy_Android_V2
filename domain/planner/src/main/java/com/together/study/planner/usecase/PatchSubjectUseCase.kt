package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerSubjectRepository
import javax.inject.Inject

class PatchSubjectUseCase @Inject constructor(
    private val repository: PlannerSubjectRepository,
) {
    suspend operator fun invoke(
        id: Long,
        originName: String,
        originColor: String,
        name: String,
        color: String,
    ): Result<Unit> {
        val requestName = if (originName==name) null else name
        val requestColor = if (originColor==color) null else color

        return repository.patchSubject(id, requestName, requestColor)
    }
}
