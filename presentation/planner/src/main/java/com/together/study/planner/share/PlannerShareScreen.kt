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
import com.together.study.calendar.model.DDay
import com.together.study.common.event.TogedyUiEvent
import com.together.study.common.event.TogedyUiEventBus
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.button.TogedyBasicButton
import com.together.study.designsystem.component.loading.TogedyLoadingScreen
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.share.component.PlannerContent
import com.together.study.planner.share.component.ShareOptionBottomSheet
import com.together.study.planner.share.component.ShareTimerSection
import com.together.study.planner.share.state.PlannerShareInfo
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
    val showTodo by viewModel.showTodo.collectAsStateWithLifecycle()

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
                showTodo = showTodo,
                modifier = modifier,
                onBackButtonClick = onBackButtonClick,
                getTargetBound = { targetBounds = it },
                onConfirmButtonClick = {
                    showEditButton = false
                    captureRequested = true
                },
                onShowTodoChanged = viewModel::updateShowTodo,
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
    plannerShareInfo: PlannerShareInfo,
    subjects: List<PlannerSubject>,
    selectedSubjects: List<Long>,
    isAllSelected: Boolean,
    showTodo: Boolean,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    getTargetBound: (Rect) -> Unit,
    onConfirmButtonClick: () -> Unit,
    onShowTodoChanged: () -> Unit,
    onSelectAllSubjectChanged: () -> Unit,
    onSubjectClick: (Long) -> Unit,
) {
    var isShareOptionVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .background(TogedyTheme.colors.white)
                .padding(top = 24.dp),
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
                        timerImageUrl = plannerShareInfo.image,
                        currentDate = plannerShareInfo.date.toLocalDate() ?: LocalDate.now(),
                        timer = plannerShareInfo.totalStudyTime,
                        dDay = plannerShareInfo.dDay,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 8.dp),
                    ) {
                        PlannerContent(
                            showTodo = showTodo,
                            plans = plannerShareInfo.plannerItemList,
                            selectedSubjects = selectedSubjects,
                            modifier = Modifier.weight(1f),
                        )

                        Spacer(Modifier.width(10.dp))

                        // TODO : TimeTable() 영역으로 변경
                        Box(
                            modifier = Modifier
                                .background(TogedyTheme.colors.gray200)
                                .height(100.dp)
                                .weight(1f),
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
            showTodo = showTodo,
            selectAllSubject = isAllSelected,
            selectedSubjects = selectedSubjects,
            onShowTodoChanged = onShowTodoChanged,
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
            plannerShareInfo = PlannerShareInfo(
                date = "2023-08-10",
                dDay = DDay(true, "수능", -100),
                totalStudyTime = "12:00:05",
                image = "",
                plannerItemList = listOf(),
                timeTableList = listOf(),
            ),
            subjects = listOf(),
            selectedSubjects = listOf(),
            isAllSelected = false,
            showTodo = true,
            onBackButtonClick = {},
            getTargetBound = {},
            onConfirmButtonClick = {},
            onShowTodoChanged = {},
            onSelectAllSubjectChanged = {},
            onSubjectClick = {},
        )
    }
}
