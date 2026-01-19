package com.together.study.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.search.model.AdmissionMethod
import com.together.study.search.model.UnivDetailSchedule
import com.together.study.search.repository.UnivScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchDetailViewModel @Inject constructor(
    private val univScheduleRepository: UnivScheduleRepository
) : ViewModel() {

    private val _univDetailScheduleState = MutableStateFlow<UiState<UnivDetailSchedule>>(UiState.Loading)
    val univDetailScheduleState: StateFlow<UiState<UnivDetailSchedule>> = _univDetailScheduleState
    
    private var currentUniversityId: Int = 0

    fun loadUniversityDetail(universityId: Int) {
        currentUniversityId = universityId
        viewModelScope.launch {
            _univDetailScheduleState.value = UiState.Loading
            
            univScheduleRepository.getUnivDetailSchedule(universityId)
                .onSuccess { detailSchedule -> 
                    _univDetailScheduleState.value = UiState.Success(detailSchedule)
                }
                .onFailure { exception -> 
                    _univDetailScheduleState.value = UiState.Failure(exception.message.toString())
                }
        }
    }

    fun deleteAddedItem(admissionMethodId: Int) {
        viewModelScope.launch {
            univScheduleRepository.deleteUnivDetailSchedule(admissionMethodId)
                .onSuccess {
                    updateDetailData(admissionMethodId, isAdd = false)
                }
                .onFailure { exception ->
                }
        }
    }

    fun addAdmissionMethod(admissionMethodId: Int) {
        viewModelScope.launch {
            val admissionMethod = AdmissionMethod(universityAdmissionMethodId = admissionMethodId)
            univScheduleRepository.addUnivDetailSchedule(admissionMethod)
                .onSuccess {
                    updateDetailData(admissionMethodId, isAdd = true)
                }
                .onFailure { exception ->
                }
        }
    }

    private fun updateDetailData(admissionMethodId: Int, isAdd: Boolean) {
        val currentState = _univDetailScheduleState.value
        if (currentState is UiState.Success) {
            val currentData = currentState.data
            val methodName = currentData.universityAdmissionMethodList
                .find { it.universityAdmissionMethodId == admissionMethodId }
                ?.universityAdmissionMethod ?: return

            val updatedList = if (isAdd) {
                if (currentData.addedAdmissionMethodList.contains(methodName)) return
                currentData.addedAdmissionMethodList + methodName
            } else {
                currentData.addedAdmissionMethodList.filter { it != methodName }
            }

            _univDetailScheduleState.value = UiState.Success(
                currentData.copy(addedAdmissionMethodList = updatedList)
            )
        }
    }
}
