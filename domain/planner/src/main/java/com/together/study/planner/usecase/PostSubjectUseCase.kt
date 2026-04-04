package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerSubjectRepository
import javax.inject.Inject

class PostSubjectUseCase @Inject constructor(
    private val repository: PlannerSubjectRepository,
) {
    suspend operator fun invoke(
        name: String,
        color: String
    ): Result<Unit> {
        return repository.postSubject(name, color)
    }
}
