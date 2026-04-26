package com.together.study.timer.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.timer.model.SubjectTimer

@Immutable
data class TimerUiState(
    val elapsedTime: Int = 0,
    val isPlaying: Boolean = false,
    val totalStudyTime: UiState<Long> = UiState.Loading,
    val subjectTimers: UiState<List<SubjectTimer>> = UiState.Loading,
    val selectedSubject: SubjectTimer? = null,
)
