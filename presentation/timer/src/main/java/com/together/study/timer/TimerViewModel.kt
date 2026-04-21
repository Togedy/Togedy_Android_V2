package com.together.study.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.timer.model.SubjectTimer
import com.together.study.timer.state.TimerUiState
import com.together.study.timer.usecase.GetSummaryTimerUseCase
import com.together.study.timer.usecase.GetTotalStudyTimerUseCase
import com.together.study.timer.usecase.StartTimerUseCase
import com.together.study.timer.usecase.StopTimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class TimerViewModel @Inject constructor(
    private val getTotalStudyTimerUseCase: GetTotalStudyTimerUseCase,
    private val getSummaryTimerUseCase: GetSummaryTimerUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val stopTimerUseCase: StopTimerUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()

    private val _elapsedTime = MutableStateFlow(0)
    val elapsedTime = _elapsedTime.asStateFlow()

    private var timerJob: Job? = null

    fun updateSelectedSubject(subject: SubjectTimer) {
        _uiState.update { it.copy(selectedSubject = subject) }
    }

    private fun updateRunningTimerId(timerId: Long?) {
        _uiState.update { it.copy(runningTimerId = timerId) }
    }

    private fun updateIsPlaying(isPlaying: Boolean = !uiState.value.isPlaying) {
        _uiState.update { it.copy(isPlaying = isPlaying) }
    }

    fun setTimerInfo() {
        getTotalTimer()
        getSubjectTimers()
    }

    fun togglePlay() {
        val selected = _uiState.value.selectedSubject ?: return
        val isPlaying = !_uiState.value.isPlaying

        if (isPlaying) startTimer(selected.subjectId)
        else stopTimer()
    }

    private fun startLocalTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _elapsedTime.update { it + 1 }
            }
        }
    }

    fun stopLocalTimer() {
        timerJob?.cancel()
    }

    private fun getTotalTimer() = viewModelScope.launch {
        getTotalStudyTimerUseCase()
            .onSuccess { response ->
                _uiState.update { it.copy(totalStudyTime = UiState.Success(response)) }
            }
    }

    private fun getSubjectTimers() = viewModelScope.launch {
        getSummaryTimerUseCase()
            .onSuccess { response ->
                _uiState.update {
                    it.copy(
                        subjectTimers = UiState.Success(response),
                        selectedSubject = response.firstOrNull(),
                    )
                }
            }
    }

    private fun startTimer(subjectId: Long) = viewModelScope.launch {
        startTimerUseCase(subjectId)
            .onSuccess {
                updateIsPlaying(true)
                updateRunningTimerId(it.timerId)
                startLocalTimer()
            }
            .onFailure { e ->
                updateIsPlaying(false)
                Timber.tag("okhttp").e("%s", e.toString())
            }
    }

    fun stopTimer() = viewModelScope.launch {
        _uiState.value.runningTimerId?.let { timerId ->
            stopTimerUseCase(timerId)
                .onFailure {
                    Timber.tag("okhttp").e("%s", it.toString())
                }
            updateRunningTimerId(null)
            updateIsPlaying(false)
            stopLocalTimer()
        }
    }
}