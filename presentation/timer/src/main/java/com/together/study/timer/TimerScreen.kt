package com.together.study.timer

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.common.type.planner.toPlannerSubjectColorOrDefault
import com.together.study.designsystem.R.drawable.ic_delete_x_16
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.timer.component.SubjectTitle
import com.together.study.timer.component.TimerButton
import com.together.study.timer.component.TimerSelectedSubject
import com.together.study.timer.model.SubjectTimer
import com.together.study.util.asColor
import com.together.study.util.noRippleClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun TimerRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val elapsedTime by viewModel.elapsedTime.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.setTimerInfo()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopLocalTimer()
            viewModel.stopTimer()
        }
    }

    val totalTime = (uiState.totalStudyTime as? UiState.Success)?.data ?: 0L
    val subjects = (uiState.subjectTimers as? UiState.Success)?.data ?: emptyList()

    TimerScreen(
        scope = scope,
        timer = formatTime(elapsedTime),
        totalTimer = formatTime(totalTime.toInt() + elapsedTime),
        selectedSubject = uiState.selectedSubject,
        subjects = subjects,
        isPlaying = uiState.isPlaying,
        modifier = modifier,
        onBackClick = onBackClick,
        onPlayButtonClick = viewModel::togglePlay,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimerScreen(
    scope: CoroutineScope,
    timer: String,
    totalTimer: String,
    selectedSubject: SubjectTimer?,
    subjects: List<SubjectTimer>,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onPlayButtonClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val scaffoldState = rememberBottomSheetScaffoldState()

    val subjectColor = selectedSubject?.subjectColor.toPlannerSubjectColorOrDefault().asColor()
    val transition = updateTransition(
        targetState = isPlaying,
        label = "playingTransition"
    )

    val radius by transition.animateFloat(
        transitionSpec = { tween(600) },
        label = "radius"
    ) { playing ->
        if (playing) 600f else 0f
    }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(400) },
        label = "alpha"
    ) { playing ->
        if (playing) 1f else 0f
    }

    val subjectCircleModifier =
        if (alpha > 0f)
            Modifier.background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        subjectColor.copy(alpha = alpha),
                        TogedyTheme.colors.black.copy(alpha = alpha)
                    ),
                    radius = radius
                )
            )
        else Modifier

    val borderColor by transition.animateColor(
        transitionSpec = { tween(300) },
        label = "borderColor"
    ) { playing ->
        if (playing) subjectColor else TogedyTheme.colors.gray700
    }

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

                    if (selectedSubject != null) {
                        TimerSelectedSubject(
                            subject = selectedSubject,
                            textColor = textColor,
                            onSubjectClick = {
                                scope.launch { scaffoldState.bottomSheetState.expand() }
                            },
                        )
                    } else {
                        TimerSelectedSubject(
                            subject = SubjectTimer(
                                subjectId = -1L,
                                subjectName = "선택",
                                subjectColor = "SUBJECT_COLOR1",
                                studyTime = 0L,
                            ),
                            textColor = textColor,
                            onSubjectClick = {
                                scope.launch { scaffoldState.bottomSheetState.expand() }
                            },
                        )
                    }

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
        selectedSubject = selectedSubject,
        subjects = subjects,
        modifier = modifier,
        onSubjectClick = {
            // 과목변경 다이얼로그
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerBottomSheet(
    scaffoldState: BottomSheetScaffoldState,
    totalTimer: String,
    selectedSubject: SubjectTimer?,
    subjects: List<SubjectTimer>,
    modifier: Modifier,
    onSubjectClick: (SubjectTimer) -> Unit,
) {
    val subjectColor = selectedSubject?.subjectColor.toPlannerSubjectColorOrDefault().asColor()

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
                            if (subject.subjectId == selectedSubject?.subjectId)
                                Modifier.background(
                                    TogedyTheme.colors.white.copy(alpha = 0.1f),
                                    RoundedCornerShape(10.dp)
                                )
                            else Modifier

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(backgroundModifier)
                                .noRippleClickable { onSubjectClick(subject) }
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
            scope = rememberCoroutineScope(),
            timer = "00:00:00",
            totalTimer = "00:00:00",
            selectedSubject = SubjectTimer(
                subjectId = 1,
                subjectName = "수학",
                subjectColor = "SUBJECT_COLOR1",
                studyTime = 0L,
            ),
            subjects = emptyList(),
            isPlaying = false,
            onBackClick = {},
            onPlayButtonClick = {},
        )
    }
}
