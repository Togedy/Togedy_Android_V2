package com.together.study.calendar.component

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.calendar.maincalendar.DailyDialogViewModel
import com.together.study.calendar.model.Schedule
import com.together.study.common.ScheduleType
import com.together.study.designsystem.R.drawable.ic_delete_24
import com.together.study.designsystem.component.AddButton
import com.together.study.designsystem.component.TogedyScheduleChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DailyScheduleDialog(
    date: LocalDate,
    dDay: Int?,
    onDismissRequest: () -> Unit,
    onScheduleItemClick: (ScheduleType, Long) -> Unit,
    onAddScheduleClick: () -> Unit,
    dailyDialogViewModel: DailyDialogViewModel,
    modifier: Modifier = Modifier,
) {
    val dailySchedules = dailyDialogViewModel.dailySchedules.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val dialogWidth = screenWidth * 0.8667f
    val dialogHeight = screenHeight * 0.65f

    LaunchedEffect(Unit) {
        dailyDialogViewModel.fetchDailySchedules(date)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = modifier
                .width(dialogWidth)
                .height(dialogHeight),
            shape = RoundedCornerShape(16.dp),
            color = TogedyTheme.colors.white,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                TopDateInfoSection(date, dDay)

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        dailySchedules.value.forEach { schedule ->
                            DailyScheduleItem(
                                coroutineScope = coroutineScope,
                                schedule = schedule,
                                onScheduleItemClick = onScheduleItemClick,
                                onDeleteClick = dailyDialogViewModel::deleteSchedule,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                AddButton(
                    title = "일정 추가",
                    onClick = onAddScheduleClick,
                )
            }
        }
    }
}

@Composable
private fun TopDateInfoSection(
    date: LocalDate,
    dDay: Int?,
    modifier: Modifier = Modifier,
) {
    val dDayColor = if (dDay == 0) TogedyTheme.colors.red else TogedyTheme.colors.gray500
    val dDayText = if (dDay == 0) "D-DAY" else "D-$dDay"

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        with(date) {
            Text(
                text = "${monthValue}월 ${dayOfMonth}일 " +
                        "${dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)}",
                style = TogedyTheme.typography.title16sb.copy(TogedyTheme.colors.gray700),
            )
        }

        if (date == LocalDate.now()) {
            Spacer(Modifier.width(4.dp))

            Text(
                text = "오늘",
                style = TogedyTheme.typography.body12m.copy(TogedyTheme.colors.white),
                modifier = Modifier
                    .background(TogedyTheme.colors.black, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }

        if (dDay != null) {
            Spacer(Modifier.weight(1f))

            Text(
                text = dDayText,
                style = TogedyTheme.typography.body14m.copy(dDayColor),
            )
        }
    }
}

@Composable
fun DailyScheduleItem(
    coroutineScope: CoroutineScope,
    schedule: Schedule,
    onScheduleItemClick: (ScheduleType, Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeOffset = remember { Animatable(0f) }
    val maxSwipe = with(LocalDensity.current) { 52.dp.toPx() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(TogedyTheme.colors.red, RoundedCornerShape(6.dp))
                .noRippleClickable { onDeleteClick(schedule.scheduleId) },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_delete_24),
                contentDescription = null,
                tint = TogedyTheme.colors.white,
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(swipeOffset.value.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            val newOffset =
                                (swipeOffset.value + dragAmount).coerceIn(-maxSwipe, 0f)
                            coroutineScope.launch {
                                swipeOffset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            coroutineScope.launch {
                                if (swipeOffset.value < -maxSwipe / 2) swipeOffset.animateTo(-maxSwipe)
                                else swipeOffset.animateTo(0f)
                            }
                        }
                    )
                }
                .background(TogedyTheme.colors.white, RoundedCornerShape(6.dp)),
        ) {
            with(schedule) {
                val scheduleType = ScheduleType.get(scheduleType)
                when (scheduleType) {
                    ScheduleType.UNIVERSITY -> {
                        TogedyScheduleChip(
                            typeStatus = when (universityAdmissionStage) {
                                "원서접수" -> 1
                                "서류제출" -> 2
                                "합격발표" -> 3
                                else -> 0
                            },
                            scheduleName = scheduleName,
                            scheduleType = universityAdmissionStage,
                            scheduleEndTime = endDate.toString(),
                            scheduleStartTime = startDate,
                        )
                    }

                    ScheduleType.USER -> {
                        UserScheduleItem(
                            schedule = schedule,
                            onScheduleItemClick = {
                                onScheduleItemClick(ScheduleType.USER, schedule.scheduleId)
                            },
                        )
                    }

                    else -> {
                        Timber.d("잘못된 ScheduleType입니다.")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DailyScheduleDialogPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TogedyTheme.colors.white),
        ) {
            DailyScheduleDialog(
                date = LocalDate.now(),
                dDay = 10,
                onScheduleItemClick = { type, id -> },
                onAddScheduleClick = {},
                onDismissRequest = {},
                dailyDialogViewModel = DailyDialogViewModel(),
                modifier = modifier,
            )
        }
    }
}
