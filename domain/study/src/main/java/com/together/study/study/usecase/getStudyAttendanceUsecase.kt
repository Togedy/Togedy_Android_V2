package com.together.study.study.usecase

import com.together.study.study.model.StudyAttendance
import com.together.study.study.repository.StudyDetailRepository
import java.time.LocalDate
import javax.inject.Inject

class GetStudyDetailInfoUseCase @Inject constructor(
    private val studyDetailRepository: StudyDetailRepository,
) {
    suspend operator fun invoke(
        studyId: Long,
        selectedDate: LocalDate,
    ): Result<List<StudyAttendance>> {
        val startDate = selectedDate.withDayOfMonth(1)
        val endDate = selectedDate.withDayOfMonth(7)

        return studyDetailRepository.getStudyAttendance(
            studyId = studyId,
            startDate = startDate.toString(),
            endDate = endDate.toString(),
        )
    }
}
