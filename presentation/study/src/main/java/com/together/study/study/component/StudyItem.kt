package com.together.study.study.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.common.type.study.StudyType
import com.together.study.designsystem.R.drawable.ic_search_24
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.model.ExploreStudyItem
import com.together.study.util.noRippleClickable
import com.together.study.util.toLocalTimeWithSecond

@Composable
internal fun StudyItem(
    study: ExploreStudyItem,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
) {
    val context = LocalContext.current

    val isChallengeType = StudyType.get(study.studyType) == StudyType.CHALLENGE
    val goalTime = study.challengeGoalTime
        ?.toLocalTimeWithSecond()
        ?.hour
        ?.toString()
        ?: ""

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .noRippleClickable(onItemClick),

        ) {
        with(study) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(studyImageUrl)
                        .placeholder(img_study_background)
                        .error(img_study_background)
                        .fallback(img_study_background)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

                if (isChallengeType) {
                    TogedyBasicTextChip(
                        text = "${goalTime}H",
                        textStyle = TogedyTheme.typography.chip10sb.copy(textAlign = TextAlign.Center),
                        backgroundColor = TogedyTheme.colors.green,
                        roundedCornerShape = RoundedCornerShape(3.dp),
                        horizontalPadding = 0.dp,
                        modifier = Modifier.width(33.dp),
                    )
                }
            }

            Spacer(Modifier.width(14.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TogedyBasicTextChip(
                        text = studyTag,
                        textStyle = TogedyTheme.typography.body10m,
                        textColor = TogedyTheme.colors.gray700,
                        horizontalPadding = 6.dp,
                        backgroundColor = TogedyTheme.colors.gray200,
                    )

                    if (isNewlyCreated) {
                        Spacer(Modifier.width(4.dp))

                        TogedyBasicTextChip(
                            text = "신규",
                            textStyle = TogedyTheme.typography.chip10sb,
                            textColor = TogedyTheme.colors.green,
                            horizontalPadding = 6.dp,
                            backgroundColor = TogedyTheme.colors.greenBg,
                        )
                    }

                    if (!lastActivatedAt.isNullOrEmpty()) {
                        Spacer(Modifier.weight(1f))

                        Text(
                            text = lastActivatedAt!!,
                            style = TogedyTheme.typography.body10m,
                            color = TogedyTheme.colors.green,
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = studyName,
                    style = TogedyTheme.typography.body14b,
                    color = TogedyTheme.colors.gray900,
                )

                Text(
                    text = studyDescription!!,
                    style = TogedyTheme.typography.body12m,
                    color = TogedyTheme.colors.gray600,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(Modifier.height(8.dp))

                MemberAndPassword(
                    context = context,
                    studyLeaderImageUrl = studyLeaderImageUrl,
                    studyMemberCount = studyMemberCount,
                    studyMemberLimit = studyMemberLimit,
                    hasPassword = hasPassword,
                )
            }
        }
    }
}

@Composable
private fun MemberAndPassword(
    context: Context,
    studyLeaderImageUrl: String,
    studyMemberCount: Int,
    studyMemberLimit: Int,
    hasPassword: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(studyLeaderImageUrl)
                .placeholder(img_study_background)
                .error(img_study_background)
                .fallback(img_study_background)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(18.dp)
                .clip(RoundedCornerShape(50.dp)),
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = TogedyTheme.colors.gray500)) {
                    append(studyMemberCount.toString())
                }
                withStyle(SpanStyle(color = TogedyTheme.colors.black)) {
                    append("/$studyMemberLimit")
                }
                withStyle(SpanStyle(color = TogedyTheme.colors.gray300)) {
                    append(" |")
                }
            },
            style = TogedyTheme.typography.body12m
        )

        if (hasPassword) {
            Spacer(Modifier.width(4.dp))

            Icon(
                imageVector = ImageVector.vectorResource(ic_search_24),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}

@Preview
@Composable
private fun ExploreStudyItemPreview() {
    TogedyTheme {
        StudyItem(
            study = ExploreStudyItem(
                studyId = 101L,
                studyType = "CHALLENGE",
                studyName = "Kotlin 개발자 되기 60일 챌린지",
                studyDescription = "하루 2시간씩 Kotlin 코드를 작성하고 서로 피드백하며 실력을 향상시키는 챌린지 스터디입니다.",
                studyTag = "일반스터디",
                studyLeaderImageUrl = "",
                studyTier = "GOLD",
                studyMemberCount = 8,
                studyMemberLimit = 10,
                studyImageUrl = "",
                isNewlyCreated = false,
                lastActivatedAt = null,
                hasPassword = true,
                challengeGoalTime = "02:00:00"
            ),
            modifier = Modifier,
            onItemClick = {},
        )
    }
}