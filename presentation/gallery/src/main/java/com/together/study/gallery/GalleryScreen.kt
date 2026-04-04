package com.together.study.gallery

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.gallery.bottomsheet.AlbumBottomSheet
import com.together.study.gallery.component.GalleryItem
import com.together.study.gallery.component.GalleryTopBar
import com.together.study.gallery.util.toUri

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun GalleryScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onImageClick: (Long) -> Unit,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val permission =
        if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_IMAGES
        else Manifest.permission.READ_EXTERNAL_STORAGE

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.load()
    }

    LaunchedEffect(Unit) {
        launcher.launch(permission)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white),
    ) {
        GalleryTopBar(
            title = uiState.selectedAlbum?.name ?: "전체 사진",
            onAlbumSelectClick = viewModel::updateAlbumSheetState,
            onBackClick = onBackClick,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
        ) {
            uiState.monthSections.forEach { section ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                    val yearMonth = section.yearMonth

                    Text(
                        text = "${yearMonth.year}년 ${yearMonth.monthValue}월",
                        modifier = Modifier.padding(16.dp),
                        style = TogedyTheme.typography.title16sb,
                        color = TogedyTheme.colors.gray700,
                    )
                }

                items(items = section.images, key = { it.id }) { image ->
                    GalleryItem(
                        imageId = image.id,
                        onClick = { onImageClick(image.id) },
                        modifier = Modifier.aspectRatio(1f),
                    )
                }
            }
        }
    }

    if (uiState.isAlbumSheetOpen && uiState.images.isNotEmpty()) {
        AlbumBottomSheet(
            albums = uiState.albums,
            entireAlbumCover = uiState.images.first().id.toUri(),
            entireCount = uiState.images.size,
            onSelect = { viewModel.selectAlbum(it) },
            onDismissRequest = viewModel::updateAlbumSheetState,
        )
    }
}

@Preview
@Composable
private fun GalleryScreenPreview() {
    TogedyTheme {
        GalleryScreen(
            onBackClick = {},
            onImageClick = {},
        )
    }
}