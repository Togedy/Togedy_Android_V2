package com.together.study.studysettings.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0
    val studyName: String = savedStateHandle.get<String>(STUDY_NAME_KEY) ?: ""

    fun deleteStudyAsMember() = viewModelScope.launch {

    }

    companion object {
        const val STUDY_ID_KEY = "studyId"
        const val STUDY_NAME_KEY = "studyName"
    }
}