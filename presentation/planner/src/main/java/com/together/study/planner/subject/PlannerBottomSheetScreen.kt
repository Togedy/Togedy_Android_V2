package com.together.study.planner.subject

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.together.study.planner.subject.state.PlannerBottomSheetState
import com.together.study.planner.subject.state.PlannerBottomSheetType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlannerBottomSheetScreen(
    bottomSheetState: PlannerBottomSheetState,
    onDismissRequest: (PlannerBottomSheetType) -> Unit,
    onPostSubject: (String, String) -> Unit,
    onEditSubjectClick: () -> Unit,
) {
    with(bottomSheetState) {
        if (isSubjectOpen) {
            SubjectBottomSheet(
                onDismissRequest = {
                    onDismissRequest(PlannerBottomSheetType.SUBJECT)
                },
                onAddSubjectClick = {
                    onDismissRequest(PlannerBottomSheetType.SUBJECT_ADD)
                },
                onEditSubjectClick = onEditSubjectClick,
            )
        }

        if (isSubjectAddOpen) {
            SubjectDetailBottomSheet(
                plannerSubject = null,
                onDismissRequest = {
                    onDismissRequest(PlannerBottomSheetType.SUBJECT_ADD)
                },
                onDoneClick = { subject ->
                    onPostSubject(subject.name, subject.color)
                    onDismissRequest(PlannerBottomSheetType.SUBJECT_ADD)
                },
            )
        }
    }
}
