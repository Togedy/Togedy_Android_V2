package com.together.study.planner.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.together.study.common.state.UiState
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
    monthlyHeatmapState: UiState<List<Int>>,
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
                subject = null,
                onDismissRequest = { onDismissRequest(PlannerSheetType.SUBJECT_ADD) },
                onDoneClick = { onDismissRequest(PlannerSheetType.SUBJECT_ADD) },
            )
        }

        if (isCalendarOpen) {
            PlannerCalendarTopSheet(
                isCalendarOpen = true,
                selectedDate = selectedDate,
                monthlyHeatmapState = monthlyHeatmapState,
                onDismissRequest = { onDismissRequest(PlannerSheetType.CALENDAR) },
                onDateChange = onDateChange,
            )
        }
    }
}
