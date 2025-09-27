package com.together.study.study.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.designsystem.component.tabbar.StudyMainTab
import com.together.study.study.main.state.MyStudyInfo
import com.together.study.study.main.state.Study
import com.together.study.study.main.state.StudyMainUiState
import com.together.study.study.main.state.TimerInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StudyMainViewModel @Inject constructor(

) : ViewModel() {
    private val _selectedTab = MutableStateFlow(StudyMainTab.MAIN)
    val selectedTab = _selectedTab.asStateFlow()

    private val _myStudyState = MutableStateFlow<UiState<MyStudyInfo>>(UiState.Loading)
    private val _exploreState = MutableStateFlow<UiState<MyStudyInfo>>(UiState.Loading)

    val studyMainUiState: StateFlow<StudyMainUiState> = combine(
        _myStudyState, _exploreState
    ) { myStudyState, exploreState ->
        StudyMainUiState(
            myStudyState = myStudyState,
            exploreState = exploreState,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = StudyMainUiState(
            myStudyState = UiState.Loading,
            exploreState = UiState.Loading,
        )
    )

    fun getStudyMainInfo() = viewModelScope.launch {
        getMyStudyInfo()
        getExploreInfo()
    }

    suspend fun getMyStudyInfo() {
        // TODO : 추후 API 연결
        _myStudyState.value = UiState.Success(
            MyStudyInfo(
                timerInfo = TimerInfo.mock1,
                studyList = listOf(
                    Study.mock1,
                    Study.mock1,
                    Study.mock1,
                    Study.mock1,
                    Study.mock1,
                    Study.mock1
                )
            )
        )
    }

    suspend fun getExploreInfo() {
        _exploreState.value = UiState.Success(
            MyStudyInfo(
                timerInfo = TimerInfo.mock1,
                studyList = listOf(
                    Study.mock1,
                    Study.mock1,
                    Study.mock1,
                    Study.mock1,
                    Study.mock1,
                    Study.mock1
                )
            )
        )
    }

    fun updateSelectedTab(new: StudyMainTab) {
        _selectedTab.value = new
    }

}
