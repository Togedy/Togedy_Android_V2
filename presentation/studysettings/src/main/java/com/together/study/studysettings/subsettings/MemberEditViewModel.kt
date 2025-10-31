package com.together.study.studysettings.subsettings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.study.model.StudyMemberBriefInfo
import com.together.study.study.type.StudyRole
import com.together.study.studysettings.type.MemberEditType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0
    val type: MemberEditType = savedStateHandle.get<MemberEditType>(TYPE_KEY) ?: MemberEditType.SHOW
    val studyMemberLimit: Int = savedStateHandle.get<Int>(STUDY_MEMBER_LIMIT_KEY) ?: 0
    private val studyMemberCount: Int = savedStateHandle.get<Int>(STUDY_MEMBER_COUNT_KEY) ?: 0
    private val _currentStudyMemberCount = MutableStateFlow(studyMemberCount)
    val currentStudyMemberCount = _currentStudyMemberCount.asStateFlow()

    private val _selectedUser = MutableStateFlow(StudyMemberBriefInfo(0, "", StudyRole.MEMBER))
    val selectedUser = _selectedUser.asStateFlow()

    fun deleteStudyMember() = viewModelScope.launch {

    }

    fun delegateLeader() = viewModelScope.launch {

    }

    fun updateSelectedUSer(user: StudyMemberBriefInfo) = viewModelScope.launch {
        _selectedUser.update { user }
    }

    companion object {
        const val STUDY_ID_KEY = "studyId"
        const val TYPE_KEY = "type"
        const val STUDY_MEMBER_LIMIT_KEY = "studyMemberLimit"
        const val STUDY_MEMBER_COUNT_KEY = "studyMemberCount"
    }
}
