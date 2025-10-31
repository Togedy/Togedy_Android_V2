package com.together.study.studysettings.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0
    val studyMemberLimit: Int = savedStateHandle.get<Int>(STUDY_MEMBER_LIMIT_KEY) ?: 0
    val studyMemberCount: Int = savedStateHandle.get<Int>(STUDY_MEMBER_COUNT_KEY) ?: 0

    fun deleteStudyAsLeader() = viewModelScope.launch {

    }

    companion object {
        const val STUDY_ID_KEY = "studyId"
        const val STUDY_MEMBER_LIMIT_KEY = "studyMemberLimit"
        const val STUDY_MEMBER_COUNT_KEY = "studyMemberCount"
    }
}
