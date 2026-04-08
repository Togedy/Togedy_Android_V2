package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerSubjectRepository
import javax.inject.Inject

class DeleteSubjectUseCase @Inject constructor(
    private val repository: PlannerSubjectRepository,
) {
    suspend operator fun invoke(id: Long): Result<Unit> {
        return repository.deleteSubject(id)
    }
}
