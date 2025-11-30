package com.togehter.study.studyupdate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudyUpdateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val studyId: Long = savedStateHandle.get<Long>(STUDY_ID_KEY) ?: 0
    val isChallenge: Boolean = savedStateHandle.get<Boolean>(IS_CHALLENGE_KEY) ?: false

    companion object {
        const val STUDY_ID_KEY = "studyId"
        const val IS_CHALLENGE_KEY = "isChallenge"
    }
}

