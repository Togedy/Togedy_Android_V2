package com.together.study.planner.usecase
import com.together.study.planner.model.SubjectItem
import com.together.study.planner.repository.PlannerSubjectRepository
import javax.inject.Inject

class GetSubjectsUseCase @Inject constructor(
    private val repository: PlannerSubjectRepository
) {
    suspend operator fun invoke(): Result<List<SubjectItem>> {
        return repository.getSubjects()
    }
}
