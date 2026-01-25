package com.together.study.planner.share

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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.model.DDay
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.button.TogedyBasicButton
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.Todo
import com.together.study.planner.share.component.PlannerContent
import com.together.study.planner.share.component.ShareTimerSection
import java.time.LocalDate

@Composable
fun PlannerShareRoute(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    PlannerShareScreen(
        context = context,
        timerImageUrl = "",
        timer = "00:00:00",
        currentDate = LocalDate.now(),
        dDay = DDay(true, "수능", 100),
        modifier = modifier,
        onBackButtonClick = {},
        onConfirmButtonClick = {},
    )
}

@Composable
fun PlannerShareScreen(
    context: Context,
    timerImageUrl: String,
    timer: String,
    currentDate: LocalDate,
    dDay: DDay,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(TogedyTheme.colors.white),
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

            Spacer(Modifier.height(18.dp))

            ShareTimerSection(
                context = context,
                timerImageUrl = timerImageUrl,
                currentDate = currentDate,
                timer = timer,
                dDay = dDay,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp),
            ) {
                PlannerContent(
                    showTodo = true,
                    plans = listOf(
                        PlannerSubject(
                            id = 1,
                            name = "국어",
                            color = "SUBJECT_COLOR1",
                            todoItems = listOf(
                                Todo(1, "할 일1", 0),
                                Todo(
                                    2,
                                    "EBS 수능특강 13강 -135page ~180page 반복 + 문풀 회독 & 14강 미리 예습해오기",
                                    1
                                ),
                            ),
                        ),
                        PlannerSubject(
                            id = 1,
                            name = "수학",
                            color = "SUBJECT_COLOR2",
                            todoItems = null,
                        ),
                    ),
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

        Column {
            Spacer(Modifier.weight(1f))

            TogedyBasicButton(
                title = "할 일 편집",
                containerColor = TogedyTheme.colors.gray300,
                contentColor = TogedyTheme.colors.gray600,
                textStyle = TogedyTheme.typography.title16sb,
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 30.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PlannerShareScreenPreview() {
    TogedyTheme {
        PlannerShareScreen(
            context = LocalContext.current,
            timerImageUrl = "",
            timer = "00:00:00",
            currentDate = LocalDate.now(),
            dDay = DDay(true, "수능", 100),
            onBackButtonClick = {},
            onConfirmButtonClick = {},
        )
    }
}