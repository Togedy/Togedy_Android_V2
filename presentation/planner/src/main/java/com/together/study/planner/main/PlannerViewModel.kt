package com.together.study.planner.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.designsystem.component.tabbar.PlannerMainTab
import com.together.study.gallery.usecase.DeleteImageUseCase
import com.together.study.planner.main.state.PlannerSheetState
import com.together.study.planner.main.state.PlannerUiState
import com.together.study.planner.model.SubjectItem
import com.together.study.planner.model.TaskItem
import com.together.study.planner.type.PlannerSheetType
import com.together.study.planner.usecase.DeleteTaskUseCase
import com.together.study.planner.usecase.GetDailyPlannerInfoUseCase
import com.together.study.planner.usecase.GetDailyStatisticsUseCase
import com.together.study.planner.usecase.GetDailyTimetableUseCase
import com.together.study.planner.usecase.GetMonthlyHeatmapUseCase
import com.together.study.planner.usecase.GetPlannerTaskListUseCase
import com.together.study.planner.usecase.GetSubjectsUseCase
import com.together.study.planner.usecase.PostSubjectUseCase
import com.together.study.planner.usecase.UpdateTaskCheckedUseCase
import com.together.study.planner.usecase.UpdateTaskContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private val deleteImageUseCase: DeleteImageUseCase,
    private val updateTaskContentUseCase: UpdateTaskContentUseCase,
    private val updateTaskCheckedUseCase: UpdateTaskCheckedUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
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

    private val isSavingTask = mutableSetOf<String>()
    private val updateJobs = mutableMapOf<String, Job>()
    private val checkJobs = mutableMapOf<Long, Job>()

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

    fun deleteImage() = viewModelScope.launch {
        deleteImageUseCase(selectedDate.value.toString())
            .onSuccess { getPlannerInfo() }
            .onFailure { e ->
                Timber.tag("okhttp-taejung").d("deleteImage: ${e.message}")
            }
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
                    it.copy(plannerSubjectState = UiState.Failure(e.message.toString()))
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

    fun updateTask(task: TaskItem, subjectId: Long) {
        val key = task.taskId?.toString() ?: task.tempId

        if (task.taskId == null && isSavingTask.contains(task.tempId)) {
            return
        }

        updateTaskInUiState(task, subjectId)
        updateJobs[key]?.cancel()
        updateJobs[key] = viewModelScope.launch {
            delay(500)
            saveTask(task, subjectId, key)
        }
    }

    fun addTempTask(subjectId: Long) {
        val newTask = TaskItem(
            tempId = java.util.UUID.randomUUID().toString(),
            taskName = ""
        )

        _uiState.update { state ->
            val current = state.plannerSubjectState
            if (current !is UiState.Success) return@update state

            val updated = current.data.map { subject ->
                if (subject.subjectId == subjectId) {
                    subject.copy(
                        tasks = subject.tasks + newTask
                    )
                } else subject
            }

            state.copy(plannerSubjectState = UiState.Success(updated))
        }
    }

    private suspend fun saveTask(task: TaskItem, subjectId: Long, key: String) {
        val isNewTask = task.taskId == null

        if (isNewTask && !isSavingTask.add(task.tempId)) return

        updateTaskContentUseCase(
            taskId = task.taskId,
            subjectId = subjectId,
            taskName = task.taskName,
            date = if (isNewTask) selectedDate.value.toString() else null,
        ).onSuccess { responseId ->
            if (isNewTask) {
                updateUiStateWithId(task.tempId, responseId)
                isSavingTask.remove(task.tempId)
            }
            updateJobs.remove(key)
        }.onFailure {
            if (isNewTask) isSavingTask.remove(task.tempId)
            updateJobs.remove(key)
        }
    }

    private fun updateTaskInUiState(task: TaskItem, subjectId: Long) {
        _uiState.update { state ->
            val current = state.plannerSubjectState
            if (current !is UiState.Success) return@update state

            val updated = current.data.map { subject ->
                if (subject.subjectId == subjectId) {
                    subject.copy(tasks = subject.tasks.map {
                        val isMatch = if (task.taskId != null) it.taskId == task.taskId
                        else it.tempId == task.tempId
                        if (isMatch) it.copy(taskName = task.taskName) else it
                    })
                } else subject
            }
            state.copy(plannerSubjectState = UiState.Success(updated))
        }
    }

    private fun updateUiStateWithId(tempId: String, realId: Long) {
        _uiState.update { state ->
            val current = state.plannerSubjectState
            if (current !is UiState.Success) return@update state

            val updatedSubjects = current.data.map { subject ->
                subject.copy(
                    tasks = subject.tasks.map {
                        if (it.tempId == tempId) it.copy(taskId = realId)
                        else it
                    }
                )
            }
            state.copy(plannerSubjectState = UiState.Success(updatedSubjects))
        }
    }

    fun updateCheckState(taskId: Long, newValue: Boolean) {
        val oldValue = !newValue
        _uiState.update { state ->
            val current = state.plannerSubjectState
            if (current !is UiState.Success) return@update state

            val updatedSubjects = current.data.map { subject ->
                subject.copy(
                    tasks = subject.tasks.map {
                        if (it.taskId == taskId) it.copy(isChecked = newValue)
                        else it
                    }
                )
            }

            state.copy(plannerSubjectState = UiState.Success(updatedSubjects))
        }

        checkJobs[taskId]?.cancel()

        checkJobs[taskId] = viewModelScope.launch {
            updateTaskCheckedUseCase(taskId, newValue)
                .onFailure {
                    _uiState.update { state ->
                        val current = state.plannerSubjectState
                        if (current !is UiState.Success) return@update state

                        val rollback = current.data.map { subject ->
                            subject.copy(
                                tasks = subject.tasks.map {
                                    if (it.taskId == taskId) it.copy(isChecked = oldValue) else it
                                }
                            )
                        }

                        state.copy(plannerSubjectState = UiState.Success(rollback))
                    }
                }
        }
    }

    fun deleteTask(taskId: Long, subjectId: Long) = viewModelScope.launch {
        deleteTaskUseCase(taskId)
            .onSuccess {
                _uiState.update { state ->
                    val current = state.plannerSubjectState
                    if (current !is UiState.Success) return@update state

                    val updatedSubjects = current.data.map { subject ->
                        if (subject.subjectId == subjectId) {
                            subject.copy(
                                tasks = subject.tasks.filterNot { it.taskId != null && it.taskId == taskId }
                            )
                        } else subject
                    }

                    state.copy(plannerSubjectState = UiState.Success(updatedSubjects))
                }
            }
    }

    fun updateSelectedDate(new: LocalDate) {
        previousDate = selectedDate.value
        _selectedDate.update { new }
        load()
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

            PlannerSheetType.IMAGE_EDIT -> {
                _sheetState.update {
                    it.copy(isImageEditOpen = !_sheetState.value.isImageEditOpen)
                }
            }
        }
    }
}
