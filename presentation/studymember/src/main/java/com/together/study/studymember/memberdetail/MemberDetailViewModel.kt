package com.together.study.studymember.memberdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.study.model.StudyMemberPlanner
import com.together.study.study.model.StudyMemberProfile
import com.together.study.study.model.StudyMemberTimeBlocks
import com.together.study.study.repository.StudyMemberRepository
import com.together.study.studymember.memberdetail.state.MemberUiState
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
internal class MemberDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studyMemberRepository: StudyMemberRepository,
) : ViewModel() {
    private var studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0
    private var memberId: Long = savedStateHandle.get<Long>(MEMBER_ID_KEY) ?: 0

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

    fun getStudyMemberInfo(studyId: Long?, memberId: Long?) = viewModelScope.launch {
        if (studyId != null && memberId != null) {
            this@MemberDetailViewModel.studyId = studyId
            this@MemberDetailViewModel.memberId = memberId
        }

        if (this@MemberDetailViewModel.studyId == 0L || this@MemberDetailViewModel.memberId == 0L) return@launch

        getStudyMemberProfile()
        getStudyMemberTimeBlocks()
        getStudyMemberPlanner()
    }

    fun getStudyMemberProfile() = viewModelScope.launch {
        studyMemberRepository.getStudyMemberProfile(studyId, memberId)
            .onSuccess { _profileState.value = UiState.Success(it) }
            .onFailure { _profileState.value = UiState.Failure(it.message.toString()) }
    }

    fun getStudyMemberTimeBlocks() = viewModelScope.launch {
        studyMemberRepository.getStudyMemberTimeBlocks(studyId, memberId)
            .onSuccess { _timeBlockState.value = UiState.Success(it) }
            .onFailure { _timeBlockState.value = UiState.Failure(it.message.toString()) }
    }

    fun getStudyMemberPlanner() = viewModelScope.launch {
        studyMemberRepository.getStudyMemberPlanner(studyId, memberId)
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
                studyMemberRepository.updatePlannerVisibility(
                    studyId,
                    memberId,
                    _isPlannerVisibleToggle.value
                )
            }

            toggleJob = null
        }
    }

    companion object {
        const val STUDY_ID_KEY = "studyId"
        const val MEMBER_ID_KEY = "memberId"
    }
}
