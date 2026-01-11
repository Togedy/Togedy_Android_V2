package com.together.study.planner.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.together.study.planner.component.PlannerCalendarTopSheet
import com.together.study.planner.main.state.PlannerSheetState
import com.together.study.planner.subject.SubjectBottomSheet
import com.together.study.planner.subject.SubjectDetailBottomSheet
import com.together.study.planner.type.PlannerSheetType
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlannerSheetScreen(
    bottomSheetState: PlannerSheetState,
    selectedDate: LocalDate,
    onDismissRequest: (PlannerSheetType) -> Unit,
    onEditSubjectClick: () -> Unit,
    onDateChange: (LocalDate) -> Unit,
) {
    with(bottomSheetState) {
        if (isSubjectOpen) {
            SubjectBottomSheet(
                onDismissRequest = { onDismissRequest(PlannerSheetType.SUBJECT) },
                onAddSubjectClick = { onDismissRequest(PlannerSheetType.SUBJECT_ADD) },
                onEditSubjectClick = onEditSubjectClick,
            )
        }

        if (isSubjectAddOpen) {
            SubjectDetailBottomSheet(
                plannerSubject = null,
                onDismissRequest = { onDismissRequest(PlannerSheetType.SUBJECT_ADD) },
                onDoneClick = { onDismissRequest(PlannerSheetType.SUBJECT_ADD) },
            )
        }

        if (isCalendarOpen) {
            PlannerCalendarTopSheet(
                isCalendarOpen = true,
                selectedDate = selectedDate,
                studyTimeList = emptyList(), //TODO: 추후 변경 필요
                onDismissRequest = { onDismissRequest(PlannerSheetType.CALENDAR) },
                onDateChange = onDateChange,
            )
        }
    }
}
