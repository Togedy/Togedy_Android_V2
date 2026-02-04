package com.together.study.planner.share.state

import androidx.compose.runtime.Immutable
import com.together.study.calendar.model.DDay
import com.together.study.common.state.UiState
import com.together.study.planner.model.PlannerItem

@Immutable
data class PlannerShareUiState(
    var plannerShareInfo: UiState<PlannerShareInfo> = UiState.Loading,
)

data class PlannerShareInfo(
    val date: String,
    val dDay: DDay,
    val totalStudyTime: String,
    val image: String,
    val plannerItemList: List<PlannerItem>,
    val timeTableList: List<Int>, //TODO: 타임플래너 부분 확인 필요
)
