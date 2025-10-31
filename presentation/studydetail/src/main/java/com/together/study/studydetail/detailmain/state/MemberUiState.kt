package com.together.study.studydetail.detailmain.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.study.model.StudyMemberPlanner
import com.together.study.study.model.StudyMemberProfile
import com.together.study.study.model.StudyMemberTimeBlocks

@Immutable
data class MemberUiState(
    val profileState: UiState<StudyMemberProfile>,
    val timeBlocksState: UiState<StudyMemberTimeBlocks>,
    val plannerState: UiState<StudyMemberPlanner>,
) {
    val isLoaded: UiState<Unit>
        get() = when {
            profileState is UiState.Loading || timeBlocksState is UiState.Loading || plannerState is UiState.Loading
                -> UiState.Loading

            profileState is UiState.Success && timeBlocksState is UiState.Success && plannerState is UiState.Success
                -> UiState.Success(Unit)

            profileState is UiState.Failure || timeBlocksState is UiState.Failure || plannerState is UiState.Failure
                -> UiState.Failure("failed to load")

            else -> UiState.Empty
        }
}
