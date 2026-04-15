package com.together.study.gallery

sealed interface ImageCropUiState {
    data object Idle : ImageCropUiState
    data object Loading : ImageCropUiState
    data class Success(val filePath: String? = null) : ImageCropUiState
    data class Error(val message: String) : ImageCropUiState
}
