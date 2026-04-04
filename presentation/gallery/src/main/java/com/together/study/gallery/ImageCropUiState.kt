package com.together.study.gallery

sealed interface ImageCropUiState {
    data object Idle : ImageCropUiState
    data object Loading : ImageCropUiState
    data object Success : ImageCropUiState
    data class Error(val message: String) : ImageCropUiState
}
