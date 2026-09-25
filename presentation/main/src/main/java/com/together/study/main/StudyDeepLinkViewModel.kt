package com.together.study.main

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.study.deeplink.PendingStudyDeepLinkStore
import com.together.study.study.usecase.ParseStudyDeepLinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyDeepLinkViewModel @Inject constructor(
    private val parseStudyDeepLinkUseCase: ParseStudyDeepLinkUseCase,
    private val pendingStudyDeepLinkStore: PendingStudyDeepLinkStore,
) : ViewModel() {

    private val _openStudy = MutableSharedFlow<Long>()
    val openStudy = _openStudy.asSharedFlow()

    fun capture(intent: Intent?, openImmediately: Boolean) {
        val studyId = parseStudyDeepLinkUseCase(intent?.data?.toString()) ?: return
        if (openImmediately) {
            viewModelScope.launch { _openStudy.emit(studyId) }
        } else {
            pendingStudyDeepLinkStore.offer(studyId)
        }
    }

    fun consumePending(): Long? = pendingStudyDeepLinkStore.consume()
}
