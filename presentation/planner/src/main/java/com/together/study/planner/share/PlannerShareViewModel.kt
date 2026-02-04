package com.together.study.planner.share

import androidx.lifecycle.ViewModel
import com.together.study.calendar.model.DDay
import com.together.study.common.state.UiState
import com.together.study.planner.model.PlannerItem
import com.together.study.planner.model.TaskItem
import com.together.study.planner.share.state.PlannerShareInfo
import com.together.study.planner.share.state.PlannerShareUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class PlannerShareViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(PlannerShareUiState())
    val uiState = _uiState.asStateFlow()

    private var _subjects = listOf<PlannerItem>().map { it.subjectId }
    private val _selectedSubjects: MutableStateFlow<List<Long>> = MutableStateFlow(emptyList())
    val selectedSubjects = _selectedSubjects.asStateFlow()
    private val _isAllSelected: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isAllSelected = _isAllSelected.asStateFlow()
    private val _showTodo: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showTodo = _showTodo.asStateFlow()

    suspend fun getPlannerShareInfo() {
        _uiState.value.plannerShareInfo = UiState.Success(
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
    }

    fun updateIsAllSelected() {
        _isAllSelected.value = !_isAllSelected.value

        if (_isAllSelected.value) _selectedSubjects.value = _subjects
        else _selectedSubjects.value = emptyList()
    }

    fun updateShowTodo() {
        _showTodo.value = !_showTodo.value
    }

    fun updateSelectedSubjects(new: Long) {
        _selectedSubjects.value += new
    }
}
