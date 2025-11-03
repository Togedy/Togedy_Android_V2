package com.together.study.studysettings.subsettings.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.study.model.StudyDetailInfo
import com.together.study.study.model.StudyMemberBriefInfo

@Immutable
data class MemberEditUiState(
    val studyInfoState: UiState<StudyDetailInfo>,
    val membersState: UiState<List<StudyMemberBriefInfo>>,
) {
    val isLoaded: UiState<Unit>
        get() = when {
            studyInfoState is UiState.Loading || membersState is UiState.Loading
                -> UiState.Loading

            studyInfoState is UiState.Success && membersState is UiState.Success
                -> UiState.Success(Unit)

            studyInfoState is UiState.Failure && membersState is UiState.Failure
                -> UiState.Failure("failed to load")

            else -> UiState.Empty
        }
}
