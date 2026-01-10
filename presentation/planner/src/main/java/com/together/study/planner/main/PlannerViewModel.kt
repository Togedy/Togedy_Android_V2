package com.together.study.planner.main

import androidx.lifecycle.ViewModel
import com.together.study.calendar.model.DDay
import com.together.study.common.state.UiState
import com.together.study.designsystem.component.tabbar.PlannerMainTab
import com.together.study.planner.main.state.PlannerInfo
import com.together.study.planner.main.state.PlannerUiState
import com.together.study.planner.subject.state.PlannerBottomSheetState
import com.together.study.planner.subject.state.PlannerBottomSheetType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class PlannerViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(PlannerUiState())
    val uiState = _uiState.asStateFlow()
    private val _bottomSheetState: MutableStateFlow<PlannerBottomSheetState> =
        MutableStateFlow(PlannerBottomSheetState())
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()
    private val _selectedTab = MutableStateFlow(PlannerMainTab.PLANNER)
    val selectedTab = _selectedTab.asStateFlow()

    fun getPlannerInfo(date: LocalDate) {
        _uiState.update {
            it.copy(
                plannerInfo = UiState.Success(
                    PlannerInfo(
                        dDay = DDay(true, "수능", -100),
                        timer = "00:00:00",
                        timerImageUrl = "",
                        plannerSubjects = emptyList()
                    )
                )
            )
        }
    }

    fun updateSelectedDate(new: LocalDate) {
        _selectedDate.update { new }
    }

    fun updateSelectedTab(new: PlannerMainTab) {
        _selectedTab.update { new }
    }

    fun updateBottomSheetVisibility(type: PlannerBottomSheetType) {
        when (type) {
            PlannerBottomSheetType.SUBJECT -> {
                _bottomSheetState.update {
                    it.copy(isSubjectOpen = !_bottomSheetState.value.isSubjectOpen)
                }
            }

            PlannerBottomSheetType.SUBJECT_ADD -> {
                _bottomSheetState.update {
                    it.copy(isSubjectAddOpen = !_bottomSheetState.value.isSubjectAddOpen)
                }
            }
        }
    }
}
