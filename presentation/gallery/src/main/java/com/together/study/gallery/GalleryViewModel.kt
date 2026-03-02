package com.together.study.gallery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.gallery.model.GalleryAlbum
import com.together.study.gallery.state.GalleryUiState
import com.together.study.gallery.usecase.GetAlbumsUseCase
import com.together.study.gallery.usecase.GetImagesUseCase
import com.together.study.gallery.usecase.GroupImagesByMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getImagesUseCase: GetImagesUseCase,
    private val groupImagesByMonthUseCase: GroupImagesByMonthUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(GalleryUiState())
        private set

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val albums = getAlbumsUseCase()
            val images = getImagesUseCase(null)
            val sections = groupImagesByMonthUseCase(images)

            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    albums = albums,
                    images = images,
                    monthSections = sections,
                )
            }
        }
    }

    fun updateAlbumSheetState() {
        uiState = uiState.copy(isAlbumSheetOpen = !uiState.isAlbumSheetOpen)
    }

    fun selectAlbum(album: GalleryAlbum?) {
        viewModelScope.launch(Dispatchers.IO) {
            val images = getImagesUseCase(album?.bucketId)
            val sections = groupImagesByMonthUseCase(images)

            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    selectedAlbum = album,
                    images = images,
                    monthSections = sections,
                    isAlbumSheetOpen = false,
                )
            }
        }
    }
}
