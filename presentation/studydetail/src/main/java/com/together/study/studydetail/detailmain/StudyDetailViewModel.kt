package com.together.study.studydetail.detailmain

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.designsystem.component.tabbar.StudyDetailTab
import com.together.study.study.main.state.Study
import com.together.study.studydetail.detailmain.state.StudyDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class StudyDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0

    private val _selectedTab = MutableStateFlow(StudyDetailTab.MEMBER)
    val selectedTab = _selectedTab.asStateFlow()
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _studyInfoState = MutableStateFlow<UiState<Study>>(UiState.Loading)
    private val _membersState = MutableStateFlow<UiState<List<StudyMember>>>(UiState.Loading)
    private val _attendanceState = MutableStateFlow<UiState<List<StudyMember>>>(UiState.Loading)

    val studyDetailUiState: StateFlow<StudyDetailUiState> = combine(
        _studyInfoState, _membersState, _attendanceState
    ) { studyInfoState, membersState, attendanceState ->
        StudyDetailUiState(
            studyInfoState = studyInfoState,
            membersState = membersState,
            attendanceState = attendanceState,
        )
    }.stateIn(
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
        // TODO : 추후 API 연결
        _studyInfoState.value = UiState.Success(Study.mock1)
    }

    suspend fun getMembers() {
        // TODO : 추후 API 연결
        _membersState.value = UiState.Success(
            listOf(
                StudyMember.mock_member,
                StudyMember.mock_leader,
                StudyMember.mock_member2,
                StudyMember.mock_member3,
                StudyMember.mock_member2,
                StudyMember.mock_member3,
                StudyMember.mock_member3,
            )
        )
    }

    fun getAttendance() = viewModelScope.launch {
        // TODO : 추후 API 연결
        _attendanceState.value = UiState.Success(
            listOf(
                StudyMember.mock_member,
                StudyMember.mock_leader,
                StudyMember.mock_member2,
                StudyMember.mock_member3,
                StudyMember.mock_member2,
                StudyMember.mock_member3,
                StudyMember.mock_member3,
            )
        )
    }

    fun updateSelectedTab(new: StudyDetailTab) {
        _selectedTab.value = new
    }

    fun updateSelectedDate(button: String) {
        if (button == "이전") {
            _selectedDate.value = _selectedDate.value.minusWeeks(1)
        } else {
            _selectedDate.value = _selectedDate.value.plusWeeks(1)
        }
        getAttendance()
    }

    companion object {
        const val STUDY_ID_KEY = "studyId"
    }
}