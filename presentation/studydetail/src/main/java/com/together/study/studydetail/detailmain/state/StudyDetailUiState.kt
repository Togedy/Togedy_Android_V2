package com.together.study.studydetail.detailmain.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.study.main.state.Study
import com.together.study.studydetail.detailmain.StudyMember

@Immutable
data class StudyDetailUiState(
    val studyInfoState: UiState<Study>,
    val membersState: UiState<List<StudyMember>>,
    val attendanceState: UiState<List<StudyMember>>, //TODO: 추후 변경
) {
    val isLoaded: UiState<Unit>
        get() = when {
            studyInfoState is UiState.Loading || membersState is UiState.Loading || attendanceState is UiState.Loading
                -> UiState.Loading

            studyInfoState is UiState.Success && membersState is UiState.Success && attendanceState is UiState.Success
                -> UiState.Success(Unit)

            studyInfoState is UiState.Failure && membersState is UiState.Failure && attendanceState is UiState.Failure
                -> UiState.Failure("failed to load")

            else -> UiState.Empty
        }
}
