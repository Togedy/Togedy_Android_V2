package com.together.study.planner.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.designsystem.component.tabbar.PlannerMainTab
import com.together.study.planner.main.state.PlannerSheetState
import com.together.study.planner.main.state.PlannerUiState
import com.together.study.planner.model.SubjectItem
import com.together.study.planner.type.PlannerSheetType
import com.together.study.planner.usecase.GetDailyPlannerInfoUseCase
import com.together.study.planner.usecase.GetDailyStatisticsUseCase
import com.together.study.planner.usecase.GetDailyTimetableUseCase
import com.together.study.planner.usecase.GetMonthlyHeatmapUseCase
import com.together.study.planner.usecase.GetPlannerTaskListUseCase
import com.together.study.planner.usecase.GetSubjectsUseCase
import com.together.study.planner.usecase.PostSubjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class PlannerViewModel @Inject constructor(
    private val getDailyPlannerInfoUseCase: GetDailyPlannerInfoUseCase,
    private val getPlannerTaskListUseCase: GetPlannerTaskListUseCase,
    private val getDailyTimetableUseCase: GetDailyTimetableUseCase,
    private val getDailyStatisticsUseCase: GetDailyStatisticsUseCase,
    private val getMonthlyHeatmapUseCase: GetMonthlyHeatmapUseCase,
    private val getSubjectsUseCase: GetSubjectsUseCase,
    private val postSubjectUseCase: PostSubjectUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlannerUiState())
    val uiState = _uiState.asStateFlow()
    private val _sheetState: MutableStateFlow<PlannerSheetState> =
        MutableStateFlow(PlannerSheetState())
    val sheetState = _sheetState.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()
    private var previousDate: LocalDate? = null
    private val _selectedTab = MutableStateFlow(PlannerMainTab.PLANNER)
    val selectedTab = _selectedTab.asStateFlow()

    fun load() = viewModelScope.launch {
        getPlannerInfo()
        getPlannerTasks()
        getTimeTable()
        getStatistics()
        if (previousDate == null || previousDate?.monthValue != selectedDate.value.monthValue) {
            getMonthlyHeatmap()
        }
        previousDate = selectedDate.value
    }

    suspend fun getPlannerInfo() {
        _uiState.update { it.copy(plannerInfoState = UiState.Loading) }
        getDailyPlannerInfoUseCase(selectedDate.value.toString())
            .onSuccess { result ->
                _uiState.update { it.copy(plannerInfoState = UiState.Success(result)) }
            }
            .onFailure { e ->
                _uiState.update {
                    Timber.tag("okhttp-chrin").d("getPlannerInfo: ${e.message}")
                    it.copy(plannerInfoState = UiState.Failure(e.message.toString()))
                }
            }
    }

    suspend fun getPlannerTasks() {
        _uiState.update { it.copy(plannerSubjectState = UiState.Loading) }
        getPlannerTaskListUseCase(selectedDate.value.toString())
            .onSuccess { result ->
                _uiState.update { it.copy(plannerSubjectState = UiState.Success(result)) }
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

    suspend fun getMonthlyHeatmap() {
        _uiState.update { it.copy(monthlyHeatmapState = UiState.Loading) }
        getMonthlyHeatmapUseCase(selectedDate.value.year, selectedDate.value.monthValue)
            .onSuccess { result ->
                _uiState.update { it.copy(monthlyHeatmapState = UiState.Success(result)) }
            }
            .onFailure { e ->
                _uiState.update {
                    it.copy(monthlyHeatmapState = UiState.Failure(e.message.toString()))
                }
            }
    }

    fun getSubjects() = viewModelScope.launch {
        getSubjectsUseCase()
            .onSuccess { result -> _uiState.update { it.copy(subjects = result) } }
            .onFailure { _uiState.update { it.copy(subjects = emptyList()) } }
    }

    fun saveNewSubject(new: SubjectItem) = viewModelScope.launch {
        postSubjectUseCase(new.subjectName, new.subjectColor)
            .onSuccess { getPlannerTasks() }
            .onFailure {
                // toast
            }
    }


    fun updateSelectedDate(new: LocalDate) {
        previousDate = selectedDate.value
        _selectedDate.update { new }
    }

    fun updateSelectedTab(new: PlannerMainTab) {
        _selectedTab.update { new }
    }

    fun updateBottomSheetVisibility(type: PlannerSheetType) {
        when (type) {
            PlannerSheetType.SUBJECT -> {
                getSubjects()
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
