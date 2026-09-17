package com.together.study.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.timer.manager.TimerManager
import com.together.study.timer.model.SubjectTimer
import com.together.study.timer.state.TimerUiState
import com.together.study.timer.usecase.GetSummaryTimerUseCase
import com.together.study.timer.usecase.GetTotalStudyTimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TimerViewModel @Inject constructor(
    private val timerManager: TimerManager,
    private val getTotalStudyTimerUseCase: GetTotalStudyTimerUseCase,
    private val getSummaryTimerUseCase: GetSummaryTimerUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeServiceState()
        loadInitialData()
    }

    private fun observeServiceState() {
        viewModelScope.launch {
            timerManager.serviceState.collect { serviceState ->
                _uiState.update {
                    it.copy(
                        elapsedTime = serviceState.elapsedTime,
                        isPlaying = serviceState.isPlaying,
                        isConnectionLost = serviceState.isConnectionLost,
                        isHeartbeatUnstable = serviceState.isHeartbeatUnstable,
                    )
                }
            }
        }
    }

    private fun loadInitialData() {
        getTotalTimer()
        getSubjectTimers()
    }

    private fun getTotalTimer() = viewModelScope.launch {
        getTotalStudyTimerUseCase()
            .onSuccess { total ->
                _uiState.update {
                    it.copy(totalStudyTime = UiState.Success(total))
                }
            }
            .onFailure { e ->
                _uiState.update { it.copy(subjectTimers = UiState.Failure(e.message.toString())) }
            }
    }

    private fun getSubjectTimers() = viewModelScope.launch {
        getSummaryTimerUseCase()
            .onSuccess { subjects ->
                _uiState.update { state ->
                    state.copy(
                        subjectTimers = UiState.Success(subjects),
                        selectedSubject = state.selectedSubject
                            ?.let { selected -> subjects.firstOrNull { it.subjectId == selected.subjectId } }
                            ?: subjects.firstOrNull(),
                    )
                }
            }
            .onFailure { e ->
                _uiState.update { it.copy(subjectTimers = UiState.Failure(e.message.toString())) }
            }
    }

    fun updateSelectedSubject(subject: SubjectTimer) {
        timerManager.resetAccumulatedTime()
        _uiState.update { it.copy(selectedSubject = subject) }
    }

    fun togglePlay() {
        val subject = _uiState.value.selectedSubject ?: return

        if (_uiState.value.isPlaying) timerManager.stop()
        else timerManager.start(subject.subjectId)
    }

    fun bindService() {
        timerManager.bind()
    }

    fun onExitTimer() {
        timerManager.stop()
        timerManager.resetAccumulatedTime()
    }

    fun onAppBackgrounded() {
        timerManager.onEnterBackground()
    }

    fun onAppForegrounded() {
        timerManager.onEnterForeground()
    }

    fun onConnectionLostConfirmed() {
        timerManager.clearConnectionLost()
        timerManager.resetAccumulatedTime()
        loadInitialData()
    }

    override fun onCleared() {
        super.onCleared()
        timerManager.unbind()
    }
}
