package com.together.study.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.designsystem.R.drawable.ic_circle_gray_24
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.user.model.UserStudyInfo

@Composable
internal fun UserStudyItem(
    studyInfo: UserStudyInfo,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp)),
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(studyInfo.studyImageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = TogedyTheme.colors.gray600.copy(alpha = 0.4f),
                blendMode = BlendMode.Darken
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(114.dp),
            error = ColorPainter(Color.White),
            placeholder = ColorPainter(Color.White),
            fallback = ColorPainter(Color.White),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = studyInfo.studyName,
                style = TogedyTheme.typography.body14m,
                color = TogedyTheme.colors.white,
            )

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_circle_gray_24),
                    contentDescription = null,
                    tint = TogedyTheme.colors.gray600,
                )

                if (studyInfo.completedMemberCount == null) {
                    Text(
                        text = "${studyInfo.studyMemberCount}명",
                        style = TogedyTheme.typography.body13m,
                        color = TogedyTheme.colors.gray600,
                    )
                } else {
                    Text(
                        text = "${studyInfo.completedMemberCount ?: "0"}/${studyInfo.studyMemberCount}명",
                        style = TogedyTheme.typography.body13m,
                        color = TogedyTheme.colors.gray600,
                    )
                }
            }
        }
    }
}
