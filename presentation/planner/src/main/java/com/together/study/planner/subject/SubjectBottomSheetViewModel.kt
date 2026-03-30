package com.together.study.planner.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.planner.model.PlannerSubject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SubjectBottomSheetViewModel @Inject constructor(

) : ViewModel() {
    private val _subjects = MutableStateFlow(listOf<PlannerSubject>())
    val subjects = _subjects.asStateFlow()

    fun getPlannerSubject() = viewModelScope.launch {
        _subjects.value = emptyList()
        // TODO: api 연결
    }
}
