package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerSubjectRepository
import javax.inject.Inject

class MoveSubjectIndexUseCase @Inject constructor(
    private val repository: PlannerSubjectRepository,
) {
    suspend operator fun invoke(
        id: Long,
        prevId: Long?,
        nextId: Long?
    ): Result<Unit> {
        return repository.moveSubjectIndex(id, prevId, nextId)
    }
}
