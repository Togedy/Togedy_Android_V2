package com.together.study.study.main.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.designsystem.R.drawable.ic_lock
import com.together.study.designsystem.R.drawable.img_character_achieved
import com.together.study.designsystem.R.drawable.img_character_challenge
import com.together.study.designsystem.R.drawable.img_character_done
import com.together.study.designsystem.R.drawable.img_character_goal
import com.together.study.designsystem.R.drawable.img_character_heart
import com.together.study.designsystem.R.drawable.img_character_speaker
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.main.state.StudyBadgeState
import com.together.study.study.model.StudyBadge
import com.together.study.util.noRippleClickable

@Composable
internal fun BadgeSection(
    badgeState: StudyBadgeState,
    onBadgeClick: (StudyBadge) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50),
        contentPadding = PaddingValues(start = 28.dp, end = 28.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            RecentBadge(
                recentBadge = badgeState.recentBadge,
                onBadgeClick = onBadgeClick,
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "리스트",
                style = TogedyTheme.typography.title16sb,
                color = TogedyTheme.colors.black,
                modifier = Modifier.padding(start = 8.dp, top = 32.dp, bottom = 16.dp),
            )
        }

        items(badgeState.badges, key = { it.badgeId }) { badge ->
            BadgeImage(
                badge = badge,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .noRippleClickable { onBadgeClick(badge) },
            )
        }
    }
}

@Composable
private fun RecentBadge(
    recentBadge: StudyBadge?,
    onBadgeClick: (StudyBadge) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (recentBadge != null) {
            BadgeImage(
                badge = recentBadge,
                modifier = Modifier
                    .size(140.dp)
                    .noRippleClickable { onBadgeClick(recentBadge) },
            )
        } else {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(TogedyTheme.colors.gray100, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "아직 획득한\n뱃지가 없어요",
                    style = TogedyTheme.typography.body12m,
                    color = TogedyTheme.colors.gray500,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun BadgeImage(
    badge: StudyBadge,
    modifier: Modifier = Modifier,
    lockIconSize: Dp = 20.dp,
) {
    val context = LocalContext.current
    val dummyImage = dummyBadgeImage(badge.badgeId)
    val isDummy = badge.badgeImageUrl == null

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(TogedyTheme.colors.gray100),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(badge.badgeImageUrl)
                .placeholder(dummyImage)
                .error(dummyImage)
                .fallback(dummyImage)
                .build(),
            contentDescription = badge.badgeName,
            contentScale = if (isDummy) ContentScale.Fit else ContentScale.Crop,
            colorFilter = if (badge.isAcquired) null else grayScaleFilter,
            alpha = if (badge.isAcquired) 1f else 0.4f,
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isDummy) 12.dp else 0.dp),
        )

        if (!badge.isAcquired) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_lock),
                contentDescription = "미획득 뱃지",
                tint = TogedyTheme.colors.gray600,
                modifier = Modifier.size(lockIconSize),
            )
        }
    }
}

@Composable
internal fun BadgeDetailDialog(
    badge: StudyBadge,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 21.dp)
                .fillMaxWidth()
                .background(TogedyTheme.colors.white, RoundedCornerShape(20.dp))
                .padding(start = 19.dp, end = 20.dp, top = 26.dp, bottom = 19.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(12.dp))

            BadgeImage(
                badge = badge,
                lockIconSize = 28.dp,
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        ambientColor = Color.Black.copy(alpha = 0.1f),
                        spotColor = Color.Black.copy(alpha = 0.1f),
                    )
                    .size(124.dp),
            )

            Spacer(Modifier.height(19.dp))

            Text(
                text = badge.badgeName,
                style = TogedyTheme.typography.title16sb,
                color = if (badge.isAcquired) TogedyTheme.colors.green else TogedyTheme.colors.gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 44.dp),
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = badge.badgeDescription,
                style = TogedyTheme.typography.body12m.copy(letterSpacing = (-0.3).sp),
                color = TogedyTheme.colors.gray500,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 44.dp),
            )

            Spacer(Modifier.height(49.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(TogedyTheme.colors.gray200)
                    .noRippleClickable(onDismissRequest)
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "닫기",
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray800,
                )
            }
        }
    }
}

private val grayScaleFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })

// TODO: 서버 뱃지 이미지 연동 전까지 사용하는 임의 이미지
@DrawableRes
private fun dummyBadgeImage(badgeId: Long): Int {
    val images = listOf(
        img_character_achieved,
        img_character_challenge,
        img_character_done,
        img_character_goal,
        img_character_heart,
        img_character_speaker,
    )
    return images[(badgeId % images.size).toInt()]
}

@Preview(showBackground = true)
@Composable
private fun BadgeSectionPreview() {
    val badges = (1L..10L).map {
        StudyBadge(
            badgeId = it,
            badgeName = "${it}월 출석왕",
            badgeDescription = "${it}월 한 달 동안 매일 출석해서\n뱃지를 받았어요",
            badgeImageUrl = null,
            isAcquired = it <= 7,
            acquiredAt = if (it <= 7) "2025-%02d-28".format(it) else null,
        )
    }

    TogedyTheme {
        BadgeSection(
            badgeState = StudyBadgeState(recentBadge = badges[6], badges = badges),
            onBadgeClick = {},
        )
    }
}

@Preview
@Composable
private fun BadgeDetailDialogPreview() {
    TogedyTheme {
        BadgeDetailDialog(
            badge = StudyBadge(
                badgeId = 100,
                badgeName = "크리스마스 파이터",
                badgeDescription = "공휴일인 크리스마스에 누적 10시간\n공부를 해서 뱃지를 받았어요",
                badgeImageUrl = null,
                isAcquired = true,
                acquiredAt = "2025-12-25",
            ),
            onDismissRequest = {},
        )
    }
}
