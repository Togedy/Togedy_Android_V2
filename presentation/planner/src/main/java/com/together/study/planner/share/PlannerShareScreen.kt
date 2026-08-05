package com.together.study.planner.share

import android.content.Context
import android.graphics.Rect
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.event.TogedyUiEvent
import com.together.study.common.event.TogedyUiEventBus
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.button.TogedyBasicButton
import com.together.study.designsystem.component.loading.TogedyLoadingScreen
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.SystemBarIcons
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.ShareInfo
import com.together.study.planner.main.ShareTimeTableContent
import com.together.study.planner.share.component.PlannerContent
import com.together.study.planner.share.component.ShareOptionBottomSheet
import com.together.study.planner.share.component.ShareTimerSection
import com.together.study.util.image.captureComposable
import com.together.study.util.image.saveBitmapToGallery
import com.together.study.util.toLocalDate
import java.time.LocalDate

@Composable
internal fun PlannerShareRoute(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlannerShareViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val subjects by viewModel.subjects.collectAsStateWithLifecycle()
    val selectedSubjects by viewModel.selectedSubjects.collectAsStateWithLifecycle()
    val isAllSelected by viewModel.isAllSelected.collectAsStateWithLifecycle()
    val showTask by viewModel.showTask.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val view = LocalView.current
    var targetBounds by remember { mutableStateOf<Rect?>(null) }
    var showEditButton by remember { mutableStateOf(true) }
    var captureRequested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showEditButton = true
        viewModel.getPlannerShareInfo()
    }

    LaunchedEffect(captureRequested) {
        if (captureRequested) {
            kotlinx.coroutines.android.awaitFrame()
            targetBounds?.let { bounds ->
                val bitmap = captureComposable(view, bounds)
                saveBitmapToGallery(viewModel.date, context, bitmap)
            }
            TogedyUiEventBus.send(
                TogedyUiEvent.ShowToast(
                    message = "이미지 저장 완료",
                    icon = ic_check_green,
                )
            )
            captureRequested = false
            onBackButtonClick()
        }
    }


    when (uiState.plannerShareInfo) {
        is UiState.Empty -> {}
        is UiState.Loading -> TogedyLoadingScreen()
        is UiState.Success -> {
            PlannerShareScreen(
                context = context,
                showEditButton = showEditButton,
                plannerShareInfo = (uiState.plannerShareInfo as UiState.Success).data,
                subjects = subjects,
                selectedSubjects = selectedSubjects,
                isAllSelected = isAllSelected,
                showTask = showTask,
                modifier = modifier,
                onBackButtonClick = onBackButtonClick,
                getTargetBound = { targetBounds = it },
                onConfirmButtonClick = {
                    showEditButton = false
                    captureRequested = true
                },
                onShowTaskChanged = viewModel::updateShowTask,
                onSelectAllSubjectChanged = viewModel::updateIsAllSelected,
                onSubjectClick = viewModel::updateSelectedSubjects,
            )
        }

        is UiState.Failure -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerShareScreen(
    context: Context,
    showEditButton: Boolean,
    plannerShareInfo: ShareInfo,
    subjects: List<PlannerSubject>,
    selectedSubjects: List<Long>,
    isAllSelected: Boolean,
    showTask: Boolean,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    getTargetBound: (Rect) -> Unit,
    onConfirmButtonClick: () -> Unit,
    onShowTaskChanged: () -> Unit,
    onSelectAllSubjectChanged: () -> Unit,
    onSubjectClick: (Long) -> Unit,
) {
    var isShareOptionVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        SystemBarIcons()

        Column(
            modifier = Modifier
                .background(TogedyTheme.colors.white)
                .systemBarsPadding(),
        ) {
            TogedyTopBar(
                title = "이미지로 공유",
                leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
                rightText = "확인",
                rightTextStyle = TogedyTheme.typography.title16sb.copy(
                    color = TogedyTheme.colors.green
                ),
                onLeftClicked = onBackButtonClick,
                onRightClicked = onConfirmButtonClick,
                modifier = Modifier.padding(top = 10.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        val rect = coordinates.boundsInWindow()
                        getTargetBound(
                            Rect(
                                rect.left.toInt(),
                                rect.top.toInt(),
                                rect.right.toInt(),
                                rect.bottom.toInt()
                            )
                        )
                    },
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 18.dp)
                ) {
                    ShareTimerSection(
                        context = context,
                        timerImageUrl = plannerShareInfo.image ?: "",
                        currentDate = plannerShareInfo.date.toLocalDate() ?: LocalDate.now(),
                        timer = plannerShareInfo.totalStudyTime,
                        hasDDay = plannerShareInfo.hasDday,
                        remainingDays = plannerShareInfo.remainingDays,
                        dDayName = plannerShareInfo.userScheduleName,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 8.dp),
                    ) {
                        PlannerContent(
                            showTask = showTask,
                            plans = plannerShareInfo.plannerItems,
                            selectedSubjects = selectedSubjects,
                            modifier = Modifier.weight(1f),
                        )

                        Spacer(Modifier.width(10.dp))

                        ShareTimeTableContent(
                            timeTables = plannerShareInfo.timeTables,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }

        if (showEditButton) {
            Column {
                Spacer(Modifier.weight(1f))

                TogedyBasicButton(
                    title = "할 일 편집",
                    containerColor = TogedyTheme.colors.gray300,
                    contentColor = TogedyTheme.colors.gray600,
                    textStyle = TogedyTheme.typography.title16sb,
                    onClick = { isShareOptionVisible = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp),
                )
            }
        }
    }

    if (isShareOptionVisible) {
        ShareOptionBottomSheet(
            subjects = subjects,
            onDismissRequest = { isShareOptionVisible = false },
            showTask = showTask,
            selectAllSubject = isAllSelected,
            selectedSubjects = selectedSubjects,
            onShowTaskChanged = onShowTaskChanged,
            onSelectAllSubjectChanged = onSelectAllSubjectChanged,
            onSubjectClick = onSubjectClick,
        )
    }
}

@Preview
@Composable
private fun PlannerShareScreenPreview() {
    TogedyTheme {
        PlannerShareScreen(
            context = LocalContext.current,
            showEditButton = true,
            plannerShareInfo = ShareInfo(
                date = "2024-06-01",
                totalStudyTime = "12:00:01",
                hasDday = true,
                remainingDays = 5,
                userScheduleName = "시험",
                plannerItems = listOf(),
                timeTables = listOf(),
                image = null,
            ),
            subjects = listOf(),
            selectedSubjects = listOf(),
            isAllSelected = false,
            showTask = true,
            onBackButtonClick = {},
            getTargetBound = {},
            onConfirmButtonClick = {},
            onShowTaskChanged = {},
            onSelectAllSubjectChanged = {},
            onSubjectClick = {},
        )
    }
}
