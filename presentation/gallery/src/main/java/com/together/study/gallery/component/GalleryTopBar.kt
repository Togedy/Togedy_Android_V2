package com.together.study.gallery.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun GalleryTopBar(
    title: String,
    onAlbumSelectClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier.noRippleClickable(onAlbumSelectClick)
            ) {
                Text(
                    text = title,
                    style = TogedyTheme.typography.title16sb,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 6.dp),
                )

                Icon(
                    imageVector = ImageVector.vectorResource(ic_left_chevron),
                    contentDescription = null,
                )
            }
        }

        Icon(
            imageVector = ImageVector.vectorResource(ic_left_chevron),
            contentDescription = "뒤로가기 버튼",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .noRippleClickable(onBackClick)
                .padding(horizontal = 16.dp),
        )
    }
}
