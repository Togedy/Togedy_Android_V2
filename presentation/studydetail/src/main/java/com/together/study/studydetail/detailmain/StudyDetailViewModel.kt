package com.together.study.studydetail.detailmain

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.designsystem.component.tabbar.StudyDetailTab
import com.together.study.study.model.StudyAttendance
import com.together.study.study.model.StudyDetailInfo
import com.together.study.study.model.StudyMember
import com.together.study.study.repository.StudyDetailRepository
import com.together.study.study.usecase.CreateStudyShareLinkUseCase
import com.together.study.study.usecase.GetStudyAttendanceUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import com.together.study.studydetail.detailmain.state.StudyDetailDialogState
import com.together.study.studydetail.detailmain.state.StudyDetailUiState
import com.together.study.studydetail.detailmain.type.StudyDetailDialogType
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
import java.time.LocalDate
import javax.inject.Inject

internal sealed interface StudyDetailUiEvent {
    data class ShareStudy(val link: String) : StudyDetailUiEvent
}

@HiltViewModel
internal class StudyDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
    private val studyDetailRepository: StudyDetailRepository,
    private val getAttendanceUseCase: GetStudyAttendanceUseCase,
    private val createStudyShareLinkUseCase: CreateStudyShareLinkUseCase,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0

    private val _selectedTab = MutableStateFlow(StudyDetailTab.MEMBER)
    val selectedTab = _selectedTab.asStateFlow()
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()
    private val _passwordErrorMessage = MutableStateFlow("")
    val passwordError = _passwordErrorMessage.asStateFlow()
    private val _dialogState: MutableStateFlow<StudyDetailDialogState> =
        MutableStateFlow(StudyDetailDialogState())
    val dialogState: StateFlow<StudyDetailDialogState> = _dialogState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<StudyDetailUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _studyInfoState = MutableStateFlow<UiState<StudyDetailInfo>>(UiState.Loading)
    private val _membersState = MutableStateFlow<UiState<List<StudyMember>>>(UiState.Loading)
    private val _attendanceState = MutableStateFlow<UiState<List<StudyAttendance>>>(UiState.Loading)

    val studyDetailUiState: StateFlow<StudyDetailUiState> = combine(
        _studyInfoState, _membersState, _attendanceState
    ) { studyInfoState, membersState, attendanceState ->
        StudyDetailUiState(
            studyInfoState = studyInfoState,
            membersState = membersState,
            attendanceState = attendanceState,
        )
    }.distinctUntilChanged().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = StudyDetailUiState(
            studyInfoState = UiState.Loading,
            membersState = UiState.Loading,
            attendanceState = UiState.Loading,
        )
    )

    fun getStudyDetailInfo() = viewModelScope.launch {
        getStudyInfo()
        getMembers()
        getAttendance()
    }

    suspend fun getStudyInfo() {
        studyDetailRepository.getStudyDetailInfo(studyId)
            .onSuccess { _studyInfoState.value = UiState.Success(it) }
            .onFailure { _studyInfoState.value = UiState.Failure(it.message.toString()) }
    }

    suspend fun getMembers() {
        studyDetailRepository.getStudyMembers(studyId)
            .onSuccess { _membersState.value = UiState.Success(it) }
            .onFailure { _membersState.value = UiState.Failure(it.message.toString()) }
    }

    suspend fun getAttendance() {
        getAttendanceUseCase(studyId, selectedDate.value)
            .onSuccess { _attendanceState.value = UiState.Success(it) }
            .onFailure { _attendanceState.value = UiState.Failure(it.message.toString()) }
    }

    fun joinStudy(password: String? = null) = viewModelScope.launch {
        _passwordErrorMessage.value = ""
        studyDetailRepository.postStudyJoin(studyId, password)
            .onSuccess {
                updateDialogState(StudyDetailDialogType.JOIN_COMPLETE)
                updateDialogState(StudyDetailDialogType.JOIN)
                getStudyDetailInfo()
            }
            .onFailure {
                _passwordErrorMessage.value = "스터디 비밀번호가 일치하지 않습니다."
                Timber.tag("okhttp-joinStudy").e(it.message.toString())
            }
    }

    fun onShareClick() {
        val link = createStudyShareLinkUseCase(studyId, context.packageName)
        viewModelScope.launch {
            _uiEvent.emit(StudyDetailUiEvent.ShareStudy(link))
        }
    }

    fun updateSelectedTab(new: StudyDetailTab) {
        _selectedTab.value = new
    }

    fun updateSelectedDate(button: String) = viewModelScope.launch {
        if (button == "이전") {
            _selectedDate.value = _selectedDate.value.minusWeeks(1)
        } else {
            _selectedDate.value = _selectedDate.value.plusWeeks(1)
        }
        getAttendance()
    }

    fun updateDialogState(dialog: StudyDetailDialogType) {
        _dialogState.update { dialogState ->
            when (dialog) {
                StudyDetailDialogType.JOIN -> {
                    dialogState.copy(isJoinDialogVisible = !dialogState.isJoinDialogVisible)
                }

                StudyDetailDialogType.JOIN_COMPLETE -> {
                    dialogState.copy(isJoinCompleteDialogVisible = !dialogState.isJoinCompleteDialogVisible)
                }

                StudyDetailDialogType.USER -> {
                    dialogState.copy(isUserBottomSheetVisible = !dialogState.isUserBottomSheetVisible)
                }
            }
        }
    }

    companion object {
        const val STUDY_ID_KEY = "studyId"
    }
}