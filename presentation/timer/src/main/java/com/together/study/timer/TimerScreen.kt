package com.together.study.timer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.common.type.planner.toPlannerSubjectColorOrDefault
import com.together.study.designsystem.R.drawable.ic_delete_x_16
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.timer.component.SubjectChangeDialog
import com.together.study.timer.component.TimerBottomSheet
import com.together.study.timer.component.TimerButton
import com.together.study.timer.component.TimerSelectedSubject
import com.together.study.timer.model.SubjectTimer
import com.together.study.timer.util.formatTime
import com.together.study.util.asColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun TimerRoute(
    onBackClick: () -> Unit,
    onNavigateToAddSubject: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    var isExitDialogVisible by remember { mutableStateOf(false) }

    val handleBack = {
        if (uiState.isPlaying) {
            isExitDialogVisible = true
        } else {
            viewModel.onExitTimer()
            onBackClick()
        }
    }

    BackHandler(onBack = handleBack)

    LaunchedEffect(Unit) {
        viewModel.bindService()
    }

    val totalTime = (uiState.totalStudyTime as? UiState.Success)?.data ?: 0L
    val subjects = (uiState.subjectTimers as? UiState.Success)?.data ?: emptyList()

    var isNoSubjectDialogVisible by remember { mutableStateOf(false) }

    TimerScreen(
        scope = scope,
        timer = formatTime(uiState.elapsedTime),
        totalTimer = formatTime(totalTime.toInt() + uiState.elapsedTime),
        elapsedTime = uiState.elapsedTime,
        selectedSubject = uiState.selectedSubject,
        subjects = subjects,
        isPlaying = uiState.isPlaying,
        modifier = modifier,
        onBackClick = handleBack,
        onPlayButtonClick = {
            if (subjects.isEmpty()) {
                isNoSubjectDialogVisible = true
            } else {
                viewModel.togglePlay()
            }
        },
        onSubjectChanged = viewModel::updateSelectedSubject,
    )

    if (isExitDialogVisible) {
        TogedyBasicDialog(
            title = "타이머 종료",
            subTitle = {
                Text(
                    text = "타이머가 진행중입니다.\n종료하시겠습니까?",
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray700,
                    textAlign = TextAlign.Center,
                )
            },
            buttonText = "종료",
            onDismissRequest = { isExitDialogVisible = false },
            onButtonClick = {
                isExitDialogVisible = false
                viewModel.onExitTimer()
                onBackClick()
            },
        )
    }

    if (isNoSubjectDialogVisible) {
        TogedyBasicDialog(
            title = "과목 추가 필요",
            subTitle = {
                Text(
                    text = "등록된 과목이 없습니다.\n과목을 추가하시겠습니까?",
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray700,
                    textAlign = TextAlign.Center,
                )
            },
            buttonText = "추가하기",
            onDismissRequest = { isNoSubjectDialogVisible = false },
            onButtonClick = {
                isNoSubjectDialogVisible = false
                viewModel.onExitTimer()
                onNavigateToAddSubject()
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimerScreen(
    scope: CoroutineScope,
    timer: String,
    totalTimer: String,
    elapsedTime: Int,
    selectedSubject: SubjectTimer?,
    subjects: List<SubjectTimer>,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onPlayButtonClick: () -> Unit,
    onSubjectChanged: (SubjectTimer) -> Unit,
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
        if (playing) 600f
        else 0f
    }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(400) },
        label = "alpha"
    ) { playing ->
        if (playing) 1f
        else 0f
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

    var isSubjectChangeDialogVisible by remember { mutableStateOf(false) }
    var tempSelectedSubject by remember { mutableStateOf<SubjectTimer?>(null) }

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
                            onSubjectClick = { scope.launch { scaffoldState.bottomSheetState.expand() } },
                        )
                    } else {
                        TimerSelectedSubject(
                            subject = SubjectTimer(
                                subjectId = -1L,
                                subjectName = "선택",
                                subjectColor = "SUBJECT_COLOR1",
                                studyTime = 0L,
                            ),
                            onSubjectClick = { scope.launch { scaffoldState.bottomSheetState.expand() } },
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
        elapsedTime = elapsedTime,
        selectedSubject = selectedSubject,
        subjects = subjects,
        modifier = modifier,
        onSubjectClick = {
            tempSelectedSubject = it
            isSubjectChangeDialogVisible = true
        },
    )

    if (isSubjectChangeDialogVisible) {
        SubjectChangeDialog(
            subjectName = tempSelectedSubject?.subjectName ?: "",
            onDismissRequest = {
                isSubjectChangeDialogVisible = false
                tempSelectedSubject = null
            },
            onConfirmClick = {
                tempSelectedSubject?.let {
                    if (selectedSubject != it) {
                        if (isPlaying) onPlayButtonClick()
                        onSubjectChanged(it)
                    }
                    isSubjectChangeDialogVisible = false
                    tempSelectedSubject = null
                }
            }
        )
    }
}

@Preview
@Composable
private fun TimerScreenPreview() {
    TogedyTheme {
        TimerScreen(
            scope = rememberCoroutineScope(),
            timer = "00:00:00",
            totalTimer = "00:00:00",
            elapsedTime = 0,
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
            onSubjectChanged = {},
        )
    }
}
