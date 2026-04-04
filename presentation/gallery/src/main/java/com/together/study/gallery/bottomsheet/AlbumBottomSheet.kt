package com.together.study.gallery.bottomsheet

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.gallery.model.GalleryAlbum
import com.together.study.gallery.util.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlbumBottomSheet(
    albums: List<GalleryAlbum>,
    entireAlbumCover: Uri,
    entireCount: Int,
    onSelect: (GalleryAlbum?) -> Unit,
    onDismissRequest: () -> Unit,
) {
    TogedyBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        modifier = Modifier.padding(horizontal = 20.dp),
        onDismissRequest = onDismissRequest,
        dragHandle = {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Box(
                    Modifier
                        .size(width = 50.dp, height = 2.dp)
                        .background(TogedyTheme.colors.gray500, RoundedCornerShape(2.dp))
                )
            }
        },
    ) {
        AlbumItem(
            title = "전체 사진",
            subtitle = "${entireCount}장",
            coverUri = entireAlbumCover,
            onClick = { onSelect(null) },
        )

        albums.forEach { album ->
            AlbumItem(
                title = album.name,
                subtitle = "${album.count}장",
                coverUri = album.bucketId.toUri(),
                onClick = { onSelect(album) },
            )
        }
    }
}

@Composable
fun AlbumItem(
    title: String,
    subtitle: String,
    coverUri: Uri,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = coverUri,
                contentDescription = null,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
            )

            Column(Modifier.padding(start = 12.dp)) {
                Text(
                    text = title,
                    style = TogedyTheme.typography.body14b,
                    color = TogedyTheme.colors.gray800
                )

                Text(
                    text = subtitle,
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray500,
                )
            }
        }

        HorizontalDivider(color = TogedyTheme.colors.gray100)
    }
}
