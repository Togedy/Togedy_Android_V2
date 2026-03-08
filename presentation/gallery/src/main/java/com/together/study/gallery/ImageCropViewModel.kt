package com.together.study.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.gallery.model.CropRequest
import com.together.study.gallery.usecase.CropImageUseCase
import com.together.study.gallery.usecase.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageCropViewModel @Inject constructor(
    private val cropImageUseCase: CropImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
) : ViewModel() {
    fun cropAndUpload(request: CropRequest) {
        viewModelScope.launch {
            cropImageUseCase(request)
                .onSuccess { filePath ->
                    uploadImageUseCase(filePath)
                }
                .onFailure {
                    //TODO toast 보여주기
                }
        }
    }
}