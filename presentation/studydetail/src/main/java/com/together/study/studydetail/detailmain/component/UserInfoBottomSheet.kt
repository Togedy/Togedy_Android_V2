package com.together.study.studydetail.detailmain.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.designsystem.R.drawable.img_character_sleeping
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.tabbar.StudyMemberTab
import com.together.study.designsystem.component.tabbar.TogedyTabBar
import com.together.study.designsystem.theme.TogedyTheme
import java.time.DayOfWeek

data class StudyMemberProfile(
    val userName: String,
    val userStatus: String,
    val userProfileImageUrl: String,
    val userProfileMessage: String,
    val totalStudyTime: String,
    val attendanceStreak: Int,
    val elapsedDays: Int,
) {
    companion object {
        val mock = StudyMemberProfile(
            "유저",
            "ACTIVE",
            "",
            "~~~",
            "1000:10:00",
            1000,
            15,
        )
    }
}

data class StudyMemberTimeBlocks(
    val studyTimeCount: Int,
    val monthlyStudyTimeList: List<MonthlyStudyTime>,
) {
    data class MonthlyStudyTime(
        val year: Int = 2025,
        val month: Int = 3,
        val studyTimeList: List<Int> = listOf(1, 2, 3, 4, 5),
    ) {
        companion object {
            val mock = MonthlyStudyTime(
                year = 2025,
                month = 3,
                studyTimeList = listOf(
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1
                )
            )
            val mock1 = MonthlyStudyTime(
                year = 2025,
                month = 4,
                studyTimeList = listOf(
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5,
                    1,
                    2,
                    3,
                    4,
                    5
                )
            )
        }
    }

    companion object {
        val mock = StudyMemberTimeBlocks(
            studyTimeCount = 15,
            monthlyStudyTimeList = listOf(
                MonthlyStudyTime.mock,
                MonthlyStudyTime.mock1,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserInfoBottomSheet(
    user: StudyMemberProfile,
    studyTimeBlocks: StudyMemberTimeBlocks,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    var selectedTab by remember { mutableStateOf(StudyMemberTab.STUDY_TIME) }
    val context = LocalContext.current
    val totalStudyTime by remember { mutableStateOf(changeToTotalStudyTime(user.totalStudyTime)) }
    val attendanceStreak by remember { mutableStateOf(checkMaxValue(user.attendanceStreak)) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(12.dp))

            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(user.userProfileImageUrl)
                    .placeholder(img_study_background)
                    .error(img_study_background)
                    .fallback(img_study_background)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(50.dp)),
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = user.userName,
                style = TogedyTheme.typography.title18b,
                color = TogedyTheme.colors.gray900,
            )

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                UserRecordBlock(
                    record = totalStudyTime,
                    type = "누적 공부시간"
                )
                UserRecordBlock(
                    record = attendanceStreak,
                    unit = "일",
                    type = "연속 출석"
                )
                UserRecordBlock(
                    record = user.elapsedDays.toString(),
                    unit = "DAY",
                    type = "스터디 시작일"
                )
            }

            Spacer(Modifier.height(20.dp))

            HorizontalDivider()

            TogedyTabBar(
                tabList = StudyMemberTab.entries,
                selectedTab = selectedTab,
                onTabChange = { selectedTab = it },
            )

            when (selectedTab) {
                StudyMemberTab.STUDY_TIME -> {
                    StudyTimeTitleSection()

                    if (studyTimeBlocks.studyTimeCount != 0) {
                        CurrentMonthlyStudyCount(
                            userName = user.userName,
                            count = studyTimeBlocks.studyTimeCount
                        )
                    }

                    LazyRow(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        itemsIndexed(studyTimeBlocks.monthlyStudyTimeList) { index, item ->
                            MonthlyStudyTimeSection(
                                year = item.year,
                                month = item.month,
                                studyTimeList = item.studyTimeList,
                            )
                        }
                    }
                }

                StudyMemberTab.PLANNER -> {}
            }
        }
    }
}

@Composable
private fun MonthlyStudyTimeSection(
    year: Int,
    month: Int,
    studyTimeList: List<Int>,
    modifier: Modifier = Modifier,
) {
    val dayOfWeek = DayOfWeek.entries

    Column(
        modifier = modifier
            .width(228.dp)
            .background(
                color = TogedyTheme.colors.gray50,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = year.toString(),
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray500,
            )

            Text(
                text = "${month}월",
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray800,
            )
        }
    }
}

@Composable
private fun CurrentMonthlyStudyCount(
    userName: String,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    color = TogedyTheme.colors.greenBg,
                    shape = RoundedCornerShape(8.dp),
                )
                .clip(RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            contentAlignment = Alignment.TopEnd,
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray800)) {
                        append("${userName}님")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray600)) {
                        append("은 이번 달 총 ")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.green)) {
                        append("${count}일")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray600)) {
                        append(" 공부했어요 \uD83D\uDD25")
                    }
                },
                style = TogedyTheme.typography.body13b,
                modifier = Modifier.align(Alignment.CenterStart),
            )

            Image(
                painter = painterResource(img_character_sleeping),
                contentDescription = null,
                modifier = Modifier
                    .width(57.dp)
                    .aspectRatio(1f)
                    .alpha(0.2f),
            )
        }
    }
}

@Composable
private fun StudyTimeTitleSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Study Tracker",
            style = TogedyTheme.typography.body14b,
            color = TogedyTheme.colors.black
        )

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "5 stack",
                style = TogedyTheme.typography.body10m,
                color = TogedyTheme.colors.gray400,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                repeat(5) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = TogedyTheme.colors.gray400,
                                shape = RoundedCornerShape(2.dp)
                            ),
                    )
                }
            }
        }
    }
}

@Composable
fun UserRecordBlock(
    record: String,
    type: String,
    unit: String = "",
    modifier: Modifier = Modifier,
) {
    val recordColor =
        if (type == "스터디 시작일") TogedyTheme.colors.green
        else TogedyTheme.colors.gray800

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = recordColor)) {
                    append(record)
                }
                withStyle(SpanStyle(color = TogedyTheme.colors.gray800)) {
                    append(unit)
                }
            },
            style = TogedyTheme.typography.body14b,
        )

        Text(
            text = type,
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray500,
        )
    }
}

private fun changeToTotalStudyTime(time: String): String {
    val times = time.split(":").map { it.toInt() }
    val hour = if (times[0] == 0) "" else if (times[0] > 999) "999+h" else "${times[0]}h"
    val minutes =
        if (times[1] == 0 || times[0] > 999) "" else if (times[1] > 999) "999+m" else "${times[1]}m"
    val space = if (hour.isNotEmpty() && minutes.isNotEmpty()) " " else ""

    return "$hour$space$minutes"
}

private fun checkMaxValue(value: Int): String {
    return if (value > 999) "999+" else value.toString()
}

@Preview
@Composable
private fun UserInfoBottomSheetPreview() {
    TogedyTheme {
        UserInfoBottomSheet(
            user = StudyMemberProfile.mock,
            studyTimeBlocks = StudyMemberTimeBlocks.mock,
            onDismissRequest = {},
        )
    }
}
