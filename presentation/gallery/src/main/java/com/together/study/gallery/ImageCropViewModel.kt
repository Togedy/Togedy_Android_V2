package com.together.study.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.together.study.gallery.model.CropRequest
import com.together.study.gallery.navigation.TogedyCropImage
import com.together.study.gallery.usecase.CropImageUseCase
import com.together.study.gallery.usecase.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageCropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cropImageUseCase: CropImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<TogedyCropImage>()

    private val _uiState = MutableStateFlow<ImageCropUiState>(ImageCropUiState.Idle)
    val uiState: StateFlow<ImageCropUiState> = _uiState.asStateFlow()

    fun cropAndUpload(request: CropRequest) {
        viewModelScope.launch {
            _uiState.value = ImageCropUiState.Loading
            cropImageUseCase(request)
                .onSuccess { filePath ->
                    uploadImageUseCase(filePath, route.date)
                        .onSuccess { _uiState.value = ImageCropUiState.Success }
                        .onFailure { _uiState.value = ImageCropUiState.Error(it.message ?: "업로드 실패") }
                }
                .onFailure {
                    _uiState.value = ImageCropUiState.Error(it.message ?: "크롭 실패")
                }
        }
    }
}