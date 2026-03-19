package com.together.study.planner.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.designsystem.component.tabbar.PlannerMainTab
import com.together.study.planner.main.state.PlannerSheetState
import com.together.study.planner.main.state.PlannerUiState
import com.together.study.planner.type.PlannerSheetType
import com.together.study.planner.usecase.GetDailyPlannerInfoUseCase
import com.together.study.planner.usecase.GetDailyStatisticsUseCase
import com.together.study.planner.usecase.GetDailyTimetableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class PlannerViewModel @Inject constructor(
    private val getDailyPlannerInfoUseCase: GetDailyPlannerInfoUseCase,
    private val getDailyTimetableUseCase: GetDailyTimetableUseCase,
    private val getDailyStatisticsUseCase: GetDailyStatisticsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlannerUiState())
    val uiState = _uiState.asStateFlow()
    private val _sheetState: MutableStateFlow<PlannerSheetState> =
        MutableStateFlow(PlannerSheetState())
    val sheetState = _sheetState.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()
    private val _selectedTab = MutableStateFlow(PlannerMainTab.PLANNER)
    val selectedTab = _selectedTab.asStateFlow()

    fun load() = viewModelScope.launch {
        getPlannerInfo()
        // 과목 조회 api 추가
        getTimeTable()
        getStatistics()
    }

    suspend fun getPlannerInfo() {
        _uiState.update { it.copy(plannerInfoState = UiState.Loading) }
        getDailyPlannerInfoUseCase(selectedDate.value.toString())
            .onSuccess { result ->
                _uiState.update { it.copy(plannerInfoState = UiState.Success(result)) }
            }
            .onFailure { e ->
                _uiState.update {
                    it.copy(plannerInfoState = UiState.Failure(e.message.toString()))
                }
            }
    }

    suspend fun getTimeTable() {
        _uiState.update { it.copy(timeTableState = UiState.Loading) }
        getDailyTimetableUseCase(selectedDate.value.toString())
            .onSuccess { result ->
                _uiState.update { it.copy(timeTableState = UiState.Success(result)) }
            }
            .onFailure { e ->
                _uiState.update {
                    it.copy(timeTableState = UiState.Failure(e.message.toString()))
                }
            }
    }

    suspend fun getStatistics() {
        _uiState.update { it.copy(statisticsState = UiState.Loading) }
        getDailyStatisticsUseCase(selectedDate.value.toString())
            .onSuccess { result ->
                _uiState.update { it.copy(statisticsState = UiState.Success(result)) }
            }
            .onFailure { e ->
                _uiState.update {
                    it.copy(statisticsState = UiState.Failure(e.message.toString()))
                }
            }
    }


    fun updateSelectedDate(new: LocalDate) {
        _selectedDate.update { new }
    }

    fun updateSelectedTab(new: PlannerMainTab) {
        _selectedTab.update { new }
    }

    fun updateBottomSheetVisibility(type: PlannerSheetType) {
        when (type) {
            PlannerSheetType.SUBJECT -> {
                _sheetState.update {
                    it.copy(isSubjectOpen = !_sheetState.value.isSubjectOpen)
                }
            }

            PlannerSheetType.SUBJECT_ADD -> {
                _sheetState.update {
                    it.copy(isSubjectAddOpen = !_sheetState.value.isSubjectAddOpen)
                }
            }

            PlannerSheetType.CALENDAR -> {
                _sheetState.update {
                    it.copy(isCalendarOpen = !_sheetState.value.isCalendarOpen)
                }
            }
        }
    }
}
