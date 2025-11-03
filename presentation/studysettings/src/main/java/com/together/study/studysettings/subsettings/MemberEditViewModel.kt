package com.together.study.studysettings.subsettings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.study.model.StudyDetailInfo
import com.together.study.study.model.StudyMemberBriefInfo
import com.together.study.study.repository.StudyDetailRepository
import com.together.study.study.repository.StudySettingsRepository
import com.together.study.study.type.StudyRole
import com.together.study.studysettings.subsettings.event.MemberEditEvent
import com.together.study.studysettings.subsettings.state.MemberEditUiState
import com.together.study.studysettings.type.MemberEditType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MemberEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studyDetailRepository: StudyDetailRepository,
    private val studySettingsRepository: StudySettingsRepository,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0
    val type: MemberEditType = savedStateHandle.get<MemberEditType>(TYPE_KEY) ?: MemberEditType.SHOW

    private val _studyInfoState = MutableStateFlow<UiState<StudyDetailInfo>>(UiState.Loading)
    private val _membersState =
        MutableStateFlow<UiState<List<StudyMemberBriefInfo>>>(UiState.Loading)

    val studyDetailUiState: StateFlow<MemberEditUiState> = combine(
        _studyInfoState, _membersState
    ) { studyInfoState, membersState ->
        MemberEditUiState(
            studyInfoState = studyInfoState,
            membersState = membersState,
        )
    }.distinctUntilChanged().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = MemberEditUiState(
            studyInfoState = UiState.Loading,
            membersState = UiState.Loading,
        )
    )

    private val _eventFlow = MutableSharedFlow<MemberEditEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _selectedUser = MutableStateFlow(StudyMemberBriefInfo(0, "", StudyRole.MEMBER))
    val selectedUser = _selectedUser.asStateFlow()

    init {
        getStudyInfo()
        getMembers()
    }

    fun getStudyInfo() = viewModelScope.launch {
        studyDetailRepository.getStudyDetailInfo(studyId)
            .onSuccess { _studyInfoState.value = UiState.Success(it) }
            .onFailure { _studyInfoState.value = UiState.Failure(it.message.toString()) }
    }

    fun getMembers() = viewModelScope.launch {
        studySettingsRepository.getStudyMembers(studyId)
            .onSuccess { _membersState.value = UiState.Success(it) }
            .onFailure { _membersState.value = UiState.Failure(it.message.toString()) }
    }

    fun deleteStudyMember() = viewModelScope.launch {
        studySettingsRepository.deleteMemberFromStudy(studyId, selectedUser.value.userId)
            .onSuccess {
                _eventFlow.emit(MemberEditEvent.DeleteMemberSuccess)
                getMembers()
            }
            .onFailure {
                _eventFlow.emit(MemberEditEvent.ShowError(it.message ?: "해당 멤버 삭제 실패했습니다."))
                Timber.tag(TAG).d("deleteStudyMember failed: $it")
            }
    }

    fun delegateLeader() = viewModelScope.launch {
        studySettingsRepository.updateStudyLeader(studyId, selectedUser.value.userId)
            .onSuccess { _eventFlow.emit(MemberEditEvent.DelegateSuccess) }
            .onFailure {
                _eventFlow.emit(MemberEditEvent.ShowError("방장 위임에 실패했습니다."))
                Timber.tag(TAG).d("delegateLeader failed: $it")
            }
    }

    fun updateSelectedUSer(user: StudyMemberBriefInfo) = viewModelScope.launch {
        _selectedUser.update { user }
    }

    companion object {
        const val TAG = "MemberEditViewModel"
        const val STUDY_ID_KEY = "studyId"
        const val TYPE_KEY = "type"
    }
}
