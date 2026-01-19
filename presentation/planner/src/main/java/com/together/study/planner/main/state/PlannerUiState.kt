package com.together.study.planner.main.state

import androidx.compose.runtime.Immutable
import com.together.study.calendar.model.DDay
import com.together.study.common.state.UiState
import com.together.study.planner.model.PlannerSubject

@Immutable
data class PlannerUiState(
    val plannerInfo: UiState<PlannerInfo> = UiState.Loading,
)

data class PlannerInfo(
    val dDay: DDay,
    val timer: String,
    val timerImageUrl: String,
    val plannerSubjects: List<PlannerSubject>,
)
