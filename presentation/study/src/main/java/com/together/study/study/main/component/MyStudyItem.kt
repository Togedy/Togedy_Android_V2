package com.together.study.study.main.component

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.common.type.study.StudyType
import com.together.study.designsystem.R.drawable.ic_delete_x_16
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.component.textchip.TogedyBorderTextChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.model.MyStudyItem
import com.together.study.study.model.MyStudyItem.ActiveMember
import com.together.study.util.noRippleClickable
import com.together.study.util.toLocalTimeWithSecond

@Composable
fun MyStudyItem(
    study: MyStudyItem,
    context: Context,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
) {
    val isChallengeType = StudyType.get(study.studyType) == StudyType.CHALLENGE

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white, RoundedCornerShape(8.dp))
            .padding(start = 14.dp, top = 12.dp, bottom = 12.dp)
            .noRippleClickable(onItemClick),
    ) {
        with(study) {
            Row(
                modifier = Modifier.padding(end = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = studyName,
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray900,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                if (isChallengeType) {
                    Spacer(Modifier.weight(1f))

                    ChallengedCount(
                        completedMemberCount = completedMemberCount!!,
                        studyMemberCount = studyMemberCount,
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            StudyingMembers(
                context = context,
                activeMemberList = activeMemberList,
            )

            if (isChallengeType) {
                Spacer(Modifier.height(8.dp))

                ChallengeGraph(
                    challengeGoalTime = challengeGoalTime!!,
                    challengeAchievement = challengeAchievement!!,
                    modifier = Modifier.padding(end = 14.dp),
                )
            }
        }
    }
}

@Composable
private fun ChallengedCount(
    completedMemberCount: Int,
    studyMemberCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_delete_x_16),
            contentDescription = null,
            modifier = Modifier.size(12.dp),
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = "$completedMemberCount/${studyMemberCount}명",
            style = TogedyTheme.typography.body13m,
            color = TogedyTheme.colors.gray600,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StudyingMembers(
    context: Context,
    activeMemberList: List<ActiveMember>?,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (activeMemberList != null && activeMemberList.isNotEmpty()) {
            stickyHeader {
                Box(
                    Modifier
                        .background(TogedyTheme.colors.white)
                        .padding(end = 4.dp),
                ) {
                    TogedyBorderTextChip(text = "공부중")
                }
            }

            items(activeMemberList) { member ->
                Row(
                    modifier = Modifier
                        .background(TogedyTheme.colors.greenBg, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context)
                            .data(member.userProfileImageUrl)
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
                        text = member.userName,
                        style = TogedyTheme.typography.body12m,
                        color = TogedyTheme.colors.green,
                    )
                }
            }
        } else {
            item {
                Text(
                    text = "아직 아무도 없네요. 한 발 더 앞서 나가볼까요?",
                    style = TogedyTheme.typography.body12m,
                    color = TogedyTheme.colors.red,
                )
            }
        }


        item {
            Spacer(Modifier.width(10.dp))
        }
    }
}

@Composable
private fun ChallengeGraph(
    challengeGoalTime: String,
    challengeAchievement: Int,
    modifier: Modifier = Modifier,
) {
    val goalTime = challengeGoalTime.toLocalTimeWithSecond().hour.toString()
    val barHeight = 4.dp
    val roundedCornerShape = RoundedCornerShape(6.dp)
    val backgroundColor = TogedyTheme.colors.gray200
    val progressColor = TogedyTheme.colors.green

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TogedyBasicTextChip(text = "${goalTime}시간", backgroundColor = progressColor)

        Spacer(Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(barHeight)
                .background(backgroundColor, roundedCornerShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(challengeAchievement * 0.01f)
                    .height(barHeight)
                    .background(progressColor, roundedCornerShape)
            )
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = "$challengeAchievement%",
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.green,
        )
    }
}

@Preview
@Composable
private fun MyStudyItemPreview() {
    TogedyTheme {
        Column {
            MyStudyItem(
                study = MyStudyItem(
                    studyId = 1,
                    studyType = "CHALLENGE",
                    challengeGoalTime = "10:00:00",
                    challengeAchievement = 75,
                    studyName = "을지중학교 1-1",
                    completedMemberCount = 3,
                    studyMemberCount = 5,
                    activeMemberList = listOf(),
                ),
                context = LocalContext.current,
                onItemClick = {},
            )
        }
    }
}
