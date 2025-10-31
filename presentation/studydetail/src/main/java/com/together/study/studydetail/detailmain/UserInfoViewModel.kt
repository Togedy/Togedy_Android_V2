package com.together.study.studydetail.detailmain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.study.model.StudyMemberPlanner
import com.together.study.study.model.StudyMemberProfile
import com.together.study.study.model.StudyMemberTimeBlocks
import com.together.study.study.repository.StudyMemberRepository
import com.together.study.studydetail.detailmain.state.MemberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UserInfoViewModel @Inject constructor(
    private val studyMemberRepository: StudyMemberRepository,
) : ViewModel() {
    var studyId: Long = 0
    var userId: Long = 0

    private val _isPlannerVisibleToggle = MutableStateFlow(false)
    val isPlannerVisibleToggle: StateFlow<Boolean> = _isPlannerVisibleToggle

    private var toggleJob: Job? = null

    private val _profileState = MutableStateFlow<UiState<StudyMemberProfile>>(UiState.Loading)
    private val _timeBlockState = MutableStateFlow<UiState<StudyMemberTimeBlocks>>(UiState.Loading)
    private val _plannerState = MutableStateFlow<UiState<StudyMemberPlanner>>(UiState.Loading)

    val memberUiState: StateFlow<MemberUiState> = combine(
        _profileState, _timeBlockState, _plannerState
    ) { profileState, timeBlockState, plannerState ->
        MemberUiState(
            profileState = profileState,
            timeBlocksState = timeBlockState,
            plannerState = plannerState,
        )
    }.distinctUntilChanged().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = MemberUiState(
            profileState = UiState.Loading,
            timeBlocksState = UiState.Loading,
            plannerState = UiState.Loading,
        )
    )

    fun getStudyMemberInfo(id: Long, user: Long) = viewModelScope.launch {
        studyId = id
        userId = user

        getStudyMemberProfile()
        getStudyMemberTimeBlocks()
        getStudyMemberPlanner()
    }

    fun getStudyMemberProfile() = viewModelScope.launch {
        studyMemberRepository.getStudyMemberProfile(studyId, userId)
            .onSuccess { _profileState.value = UiState.Success(it) }
            .onFailure { _profileState.value = UiState.Failure(it.message.toString()) }
    }

    fun getStudyMemberTimeBlocks() = viewModelScope.launch {
        studyMemberRepository.getStudyMemberTimeBlocks(studyId, userId)
            .onSuccess { _timeBlockState.value = UiState.Success(it) }
            .onFailure { _timeBlockState.value = UiState.Failure(it.message.toString()) }
    }

    fun getStudyMemberPlanner() = viewModelScope.launch {
        studyMemberRepository.getStudyMemberPlanner(studyId, userId)
            .onSuccess { _plannerState.value = UiState.Success(it) }
            .onFailure { _plannerState.value = UiState.Failure(it.message.toString()) }
    }

    fun onPlannerVisibleToggleClicked(initialValue: Boolean) {
        toggleJob?.cancel()

        val newValue = !_isPlannerVisibleToggle.value
        _isPlannerVisibleToggle.value = newValue

        toggleJob = viewModelScope.launch {
            delay(3000L)

            if (_isPlannerVisibleToggle.value != initialValue) {
                studyMemberRepository.postPlannerVisibility(
                    studyId,
                    userId,
                    _isPlannerVisibleToggle.value
                )
            }

            toggleJob = null
        }
    }
}
