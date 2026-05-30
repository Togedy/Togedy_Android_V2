package com.together.study.mypage.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.mypage.usecase.GetTermsOfServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TermsOfServiceViewModel @Inject constructor(
    private val getTermsOfServiceUseCase: GetTermsOfServiceUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val uiState: StateFlow<UiState<String>> = _uiState.asStateFlow()

    fun getTermsOfService() = viewModelScope.launch {
        getTermsOfServiceUseCase()
            .onSuccess { _uiState.value = UiState.Success(it) }
            .onFailure { _uiState.value = UiState.Failure(it.toString()) }
    }
}