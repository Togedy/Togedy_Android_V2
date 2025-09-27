package com.together.study.study.main.component

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.type.StudyType
import com.together.study.designsystem.R.drawable.ic_delete_x_16
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.component.textchip.TogedyBorderTextChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.main.state.Study
import com.together.study.study.main.state.User
import com.together.study.util.noRippleClickable
import com.together.study.util.toLocalTime

@Composable
fun MyStudyItem(
    study: Study,
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

            StudyingMembers(activeMemberList)

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
    activeMemberList: List<User>,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        stickyHeader {
            Box(
                Modifier
                    .background(TogedyTheme.colors.white)
                    .padding(end = 4.dp),
            ) {
                TogedyBorderTextChip(text = "공부중")
            }
        }

        items(activeMemberList) {
            Row(
                modifier = Modifier
                    .background(TogedyTheme.colors.greenBg, RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    Modifier
                        .size(18.dp)
                        .background(TogedyTheme.colors.gray500)
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = it.userName,
                    style = TogedyTheme.typography.body12m,
                    color = TogedyTheme.colors.green,
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
    val goalTime = challengeGoalTime.toLocalTime().hour.toString()
    val barHeight = 4.dp
    val roundedCornerShape = RoundedCornerShape(6.dp)
    val backgroundColor = TogedyTheme.colors.gray200
    val progressColor = TogedyTheme.colors.green

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TogedyBasicTextChip(text = goalTime, backgroundColor = progressColor)

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
                study = Study.mock1,
                onItemClick = {},
            )
            MyStudyItem(
                study = Study.mock2,
                onItemClick = {},
            )
        }
    }
}
