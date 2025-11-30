package com.togehter.study.studyupdate

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudyUpdateDoneViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val studyName: String = savedStateHandle.get<String>(STUDY_NAME_KEY) ?: ""
    val studyIntroduce: String = savedStateHandle.get<String>(STUDY_INTRODUCE_KEY) ?: ""
    val studyCategory: String? =
        savedStateHandle.get<String>(STUDY_CATEGORY_KEY)?.takeIf { it.isNotEmpty() }
    val studyImageUri: Uri? =
        savedStateHandle.get<String>(STUDY_IMAGE_URI_KEY)?.takeIf { it.isNotEmpty() }?.toUri()
    val memberCount: Int? = savedStateHandle.get<Int>(MEMBER_COUNT_KEY)?.takeIf { it > 0 }
    val isChallenge: Boolean = savedStateHandle.get<Boolean>(IS_CHALLENGE_KEY) ?: false

    companion object {
        const val STUDY_NAME_KEY = "studyName"
        const val STUDY_INTRODUCE_KEY = "studyIntroduce"
        const val STUDY_CATEGORY_KEY = "studyCategory"
        const val STUDY_IMAGE_URI_KEY = "studyImageUri"
        const val MEMBER_COUNT_KEY = "memberCount"
        const val IS_CHALLENGE_KEY = "isChallenge"
    }
}

