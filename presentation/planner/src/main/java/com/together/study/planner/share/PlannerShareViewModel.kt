package com.together.study.planner.share

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.together.study.calendar.model.DDay
import com.together.study.common.state.UiState
import com.together.study.planner.model.PlannerItem
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.TaskItem
import com.together.study.planner.navigation.PlannerShare
import com.together.study.planner.share.state.PlannerShareInfo
import com.together.study.planner.share.state.PlannerShareUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class PlannerShareViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val route: PlannerShare = savedStateHandle.toRoute<PlannerShare>()
    val year = route.year
    val month = route.month
    val day = route.day
    val date = "$year$month$day"

    private val _uiState = MutableStateFlow(PlannerShareUiState())
    val uiState = _uiState.asStateFlow()

    private val _subjects: MutableStateFlow<List<PlannerSubject>> = MutableStateFlow(emptyList())
    val subjects = _subjects.asStateFlow()
    private val _selectedSubjects: MutableStateFlow<List<Long>> = MutableStateFlow(emptyList())
    val selectedSubjects = _selectedSubjects.asStateFlow()
    private val _isAllSelected: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isAllSelected = _isAllSelected.asStateFlow()
    private val _showTodo: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showTodo = _showTodo.asStateFlow()

    suspend fun getPlannerShareInfo() {
        _uiState.update {
            it.copy(
                plannerShareInfo = UiState.Success(
                    PlannerShareInfo(
                        date = "",
                        dDay = DDay(true, "수능", -100),
                        totalStudyTime = "00:00:00",
                        image = "",
                        plannerItemList = listOf(
                            PlannerItem(
                                subjectId = 1,
                                subjectName = "수학",
                                subjectColor = "SUBJECT_COLOR2",
                                totalTaskCount = 5,
                                checkedTaskCount = 3,
                                taskList = listOf(
                                    TaskItem(1, "할 일1", false),
                                    TaskItem(2, "EBS 수학", true),
                                )
                            ),
                            PlannerItem(
                                subjectId = 2,
                                subjectName = "수학",
                                subjectColor = "SUBJECT_COLOR2",
                                totalTaskCount = 5,
                                checkedTaskCount = 3,
                                taskList = listOf(
                                    TaskItem(1, "할 일1", false),
                                    TaskItem(2, "EBS 수학", true),
                                    TaskItem(1, "할 일1", false),
                                    TaskItem(2, "EBS 수학", true),
                                    TaskItem(1, "할 일1", false),
                                    TaskItem(2, "EBS 수학", true),
                                )
                            ),
                        ),
                        timeTableList = listOf(),
                    )
                )
            )
        }

        _subjects.value =
            (_uiState.value.plannerShareInfo as UiState.Success).data.plannerItemList.map {
                PlannerSubject(
                    it.subjectId,
                    it.subjectName,
                    it.subjectColor,
                )
            }
        _selectedSubjects.value = _subjects.value.mapNotNull { it.subjectId }
    }

    fun updateIsAllSelected() {
        _isAllSelected.value = !_isAllSelected.value

        if (_isAllSelected.value) _selectedSubjects.value =
            subjects.value.mapNotNull { it.subjectId }
        else _selectedSubjects.value = emptyList()
    }

    fun updateShowTodo() {
        _showTodo.value = !_showTodo.value
    }

    fun updateSelectedSubjects(new: Long) {
        if (_selectedSubjects.value.contains(new)) _selectedSubjects.value -= new
        else _selectedSubjects.value += new

        _isAllSelected.value = _selectedSubjects.value.size == subjects.value.size
    }
}
