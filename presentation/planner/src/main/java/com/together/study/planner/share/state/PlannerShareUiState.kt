package com.together.study.planner.share.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.planner.model.ShareInfo

@Immutable
data class PlannerShareUiState(
    val plannerShareInfo: UiState<ShareInfo> = UiState.Loading,
)
