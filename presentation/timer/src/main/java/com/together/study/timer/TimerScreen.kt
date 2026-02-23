package com.together.study.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.type.planner.toPlannerSubjectColorOrDefault
import com.together.study.designsystem.R.drawable.ic_delete_x_16
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.timer.component.SubjectTitle
import com.together.study.timer.component.TimerButton
import com.together.study.timer.component.TimerSelectedSubject
import com.together.study.util.asColor
import kotlinx.coroutines.delay

@Composable
internal fun TimerRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isPlaying by remember { mutableStateOf(false) }
    var timer by remember { mutableIntStateOf(0) }
    var totalTimer by remember { mutableIntStateOf(0) }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(1000L)
            timer += 1
            totalTimer += 1
        }
    }

    TimerScreen(
        timer = formatTime(timer),
        totalTimer = formatTime(totalTimer),
        subject = PlannerSubject(
            subjectId = 1,
            subjectName = "수학",
            subjectColor = "SUBJECT_COLOR1",
        ),
        isPlaying = isPlaying,
        modifier = modifier,
        onBackClick = onBackClick,
        onPlayButtonClick = { isPlaying = !isPlaying },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimerScreen(
    timer: String,
    totalTimer: String,
    subject: PlannerSubject,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onPlayButtonClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val scaffoldState = rememberBottomSheetScaffoldState()

    val subjectColor = subject.subjectColor.toPlannerSubjectColorOrDefault().asColor()
    val subjectCircleModifier =
        if (isPlaying)
            Modifier.background(
                brush = Brush.radialGradient(
                    colors = listOf(subjectColor, TogedyTheme.colors.black),
                    radius = 600f,
                ),
            )
        else Modifier
    val borderColor =
        if (isPlaying) subjectColor
        else TogedyTheme.colors.gray700
    val textColor =
        if (isPlaying) TogedyTheme.colors.gray600
        else TogedyTheme.colors.white

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.black)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TogedyTopBar(
            title = "Timer",
            titleStyle = TogedyTheme.typography.title16sb.copy(color = TogedyTheme.colors.white),
            leftIcon = ImageVector.vectorResource(ic_delete_x_16),
            leftIconColor = TogedyTheme.colors.white,
            onLeftClicked = onBackClick,
        )

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(screenWidthDp * 1.5f)
                .then(subjectCircleModifier),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(500.dp))
                    .border(
                        2.dp,
                        borderColor.copy(alpha = 0.8f),
                        RoundedCornerShape(500.dp),
                    )
                    .background(
                        color = TogedyTheme.colors.black,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.matchParentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.weight(1f))

                    TimerSelectedSubject(
                        subject = subject,
                        textColor = textColor,
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = timer,
                        style = TogedyTheme.typography.time40b,
                        color = TogedyTheme.colors.white
                    )

                    Spacer(Modifier.height(20.dp))

                    TimerButton(
                        isPlaying = isPlaying,
                        onButtonClick = onPlayButtonClick,
                    )

                    Spacer(Modifier.weight(1f))
                }
            }
        }

        Spacer(Modifier.weight(2f))
    }

    TimerBottomSheet(
        scaffoldState = scaffoldState,
        totalTimer = totalTimer,
        selectedSubject = subject,
        subjects = listOf(
            subject,
            PlannerSubject(2, "과학", "SUBJECT_COLOR2"),
            PlannerSubject(3, "영어", "SUBJECT_COLOR3"),
            PlannerSubject(4, "국어", "SUBJECT_COLOR4"),
            PlannerSubject(5, "수학", "SUBJECT_COLOR5"),
            PlannerSubject(6, "사회", "SUBJECT_COLOR6"),
            PlannerSubject(7, "과학", "SUBJECT_COLOR7"),
            PlannerSubject(8, "영어", "SUBJECT_COLOR8"),
            PlannerSubject(9, "국어", "SUBJECT_COLOR9"),
        ),
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerBottomSheet(
    scaffoldState: BottomSheetScaffoldState,
    totalTimer: String,
    selectedSubject: PlannerSubject,
    subjects: List<PlannerSubject>,
    modifier: Modifier,
) {
    val subjectColor = selectedSubject.subjectColor.toPlannerSubjectColorOrDefault().asColor()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 130.dp,
        sheetContainerColor = Color.Transparent,
        containerColor = Color.Transparent,
        sheetDragHandle = {
        },
        sheetContent = {
            Column(
                modifier
                    .fillMaxWidth()
                    .aspectRatio(534f / 720f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                subjectColor,
                                subjectColor.copy(alpha = 0.7f)
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(width = 42.dp, height = 2.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.6f))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, bottom = 20.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "총 공부시간",
                        style = TogedyTheme.typography.body14b,
                        color = TogedyTheme.colors.white,
                    )

                    Text(
                        text = totalTimer,
                        style = TogedyTheme.typography.time46r,
                        color = TogedyTheme.colors.white,
                    )
                }

                HorizontalDivider(color = TogedyTheme.colors.white.copy(alpha = 0.2f))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                ) {
                    item {
                        Spacer(Modifier.height(10.dp))
                    }

                    items(subjects) { subject ->
                        val backgroundModifier =
                            if (subject.subjectId == selectedSubject.subjectId)
                                Modifier.background(
                                    TogedyTheme.colors.white.copy(alpha = 0.1f),
                                    RoundedCornerShape(10.dp)
                                )
                            else Modifier

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(backgroundModifier)
                                .padding(horizontal = 10.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            SubjectTitle(subject = subject)

                            Text(
                                text = totalTimer,
                                style = TogedyTheme.typography.time40l,
                                color = TogedyTheme.colors.white,
                            )
                        }
                    }

                    item {
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
    ) { }
}

private fun formatTime(totalSeconds: Int): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}

@Preview
@Composable
private fun TimerScreenPreview() {
    TogedyTheme {
        TimerScreen(
            timer = "00:00:00",
            totalTimer = "00:00:00",
            subject = PlannerSubject(
                subjectId = 1,
                subjectName = "수학",
                subjectColor = "SUBJECT_COLOR1",
            ),
            isPlaying = false,
            onBackClick = {},
            onPlayButtonClick = {},
        )
    }
}