package com.together.study.planner.main.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.planner.model.DailyPlannerInfo
import com.together.study.planner.model.DailyStatistics
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.TimeTable

@Immutable
data class PlannerUiState(
    val plannerInfoState: UiState<DailyPlannerInfo> = UiState.Loading,
    val plannerSubjectState: UiState<List<PlannerSubject>> = UiState.Loading,
    val timeTableState: UiState<List<TimeTable>> = UiState.Loading,
    val statisticsState: UiState<DailyStatistics> = UiState.Loading,
)
