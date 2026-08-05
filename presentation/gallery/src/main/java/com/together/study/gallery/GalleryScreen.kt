package com.together.study.gallery

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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.together.study.designsystem.theme.SystemBarIcons
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.gallery.bottomsheet.AlbumBottomSheet
import com.together.study.gallery.component.GalleryItem
import com.together.study.gallery.component.GalleryTopBar
import com.together.study.gallery.component.MediaAccessDeniedContent
import com.together.study.gallery.component.PartialAccessBanner
import com.together.study.gallery.util.MediaAccessLevel
import com.together.study.gallery.util.mediaAccessLevel
import com.together.study.gallery.util.mediaPermissions
import com.together.study.gallery.util.toUri
import com.together.study.util.openAppSettings

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun GalleryScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onImageClick: (Long) -> Unit,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    var accessLevel by remember { mutableStateOf(context.mediaAccessLevel()) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        accessLevel = context.mediaAccessLevel()
        if (accessLevel != MediaAccessLevel.DENIED) viewModel.load()
    }

    LaunchedEffect(Unit) {
        if (accessLevel == MediaAccessLevel.DENIED) launcher.launch(mediaPermissions)
        else viewModel.load()
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        // 설정 콜백 시 변경
        val currentAccessLevel = context.mediaAccessLevel()
        val isAccessLevelChanged = currentAccessLevel != accessLevel
        accessLevel = currentAccessLevel

        when {
            currentAccessLevel == MediaAccessLevel.DENIED -> Unit
            isAccessLevelChanged || currentAccessLevel == MediaAccessLevel.PARTIAL -> viewModel.load()
        }
    }

    SystemBarIcons()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .systemBarsPadding(),
    ) {
        GalleryTopBar(
            title = uiState.selectedAlbum?.name ?: "전체 사진",
            onAlbumSelectClick = viewModel::updateAlbumSheetState,
            onBackClick = onBackClick,
        )

        if (accessLevel == MediaAccessLevel.DENIED) {
            MediaAccessDeniedContent(onSettingsClick = context::openAppSettings)
        } else {
            if (accessLevel == MediaAccessLevel.PARTIAL) {
                PartialAccessBanner(onManageClick = { launcher.launch(mediaPermissions) })
            }

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