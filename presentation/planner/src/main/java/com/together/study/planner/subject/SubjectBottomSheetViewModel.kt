package com.together.study.planner.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.planner.model.SubjectItem
import com.together.study.planner.usecase.GetSubjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class SubjectBottomSheetViewModel @Inject constructor(
    private val getSubjectUseCase: GetSubjectsUseCase,
) : ViewModel() {
    private val _subjects = MutableStateFlow(listOf<SubjectItem>())
    val subjects = _subjects.asStateFlow()

    fun getPlannerSubject() = viewModelScope.launch {
        getSubjectUseCase()
            .onSuccess { result -> _subjects.value = result }
            .onFailure {
                Timber.tag("okhttp-SubjectBottomSheet")
                    .d("getPlannerSubject: ${it.message.toString()}")
            }
    }
}
