package com.together.study.study.usecase

import com.together.study.study.model.StudyAttendance
import com.together.study.study.repository.StudyDetailRepository
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class GetStudyAttendanceUseCase @Inject constructor(
    private val studyDetailRepository: StudyDetailRepository,
) {
    suspend operator fun invoke(
        studyId: Long,
        selectedDate: LocalDate,
    ): Result<List<StudyAttendance>> {
        val startDate = selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endDate = selectedDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

        return studyDetailRepository.getStudyAttendance(
            studyId = studyId,
            startDate = startDate.toString(),
            endDate = endDate.toString(),
        )
    }
}
