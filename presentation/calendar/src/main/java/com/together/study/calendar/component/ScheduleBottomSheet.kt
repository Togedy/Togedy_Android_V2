package com.together.study.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.model.Category
import com.together.study.calendar.type.toCategoryColorOrDefault
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.presentation.calendar.R.drawable.ic_category_box
import com.together.study.presentation.calendar.R.drawable.ic_empty_category_box
import com.together.study.util.noRippleClickable
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScheduleBottomSheet(
    scheduleId: Long? = null,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomSheetHeight = screenHeight * 0.63f

    var scheduleName by remember { mutableStateOf("") }
    val title = if (scheduleId == null) "일정추가" else "일정수정"

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = title,
        showDone = true,
        onDoneClick = { /*TODO: API 연결 및 바텀시트 종료*/ },
        modifier = modifier
            .fillMaxWidth()
            .height(bottomSheetHeight),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            ScheduleNameSection(
                scheduleName = scheduleName,
                categoryColor = TogedyTheme.colors.gray300,
                onNameChange = { scheduleName = it },
            )

            Spacer(Modifier.height(24.dp))

            ScheduleDateTimeSection(
                startDateTime = Pair(LocalDate.now(), "오후 12:00"),
                endDateTime = Pair(LocalDate.now(), null),
                onCalendarOpen = { },
                onClockOpen = { },
            )

            Spacer(Modifier.height(24.dp))

            ScheduleCategorySection(
                category = Category(1, "국어학원", "CATEGORY_COLOR11"),
                onCategoryClick = { },
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ScheduleCategorySection(
    category: Category?,
    onCategoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .noRippleClickable(onCategoryClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (category == null) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_empty_category_box),
                contentDescription = null,
                tint = TogedyTheme.colors.gray300,
            )

            Spacer(Modifier.width(8.dp))

            GrayBoxText(
                text = "카테고리",
                textStyle = TogedyTheme.typography.chip14b,
                textColor = TogedyTheme.colors.gray400,
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(ic_category_box),
                contentDescription = null,
                tint = category.categoryColor.toCategoryColorOrDefault(),
            )

            Spacer(Modifier.width(8.dp))

            GrayBoxText(
                text = category.categoryName.toString(),
                textStyle = TogedyTheme.typography.chip14b,
                textColor = category.categoryColor.toCategoryColorOrDefault(),
            )
        }
    }
}

@Composable
private fun ScheduleNameSection(
    scheduleName: String,
    categoryColor: Color,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textStyle = TogedyTheme.typography.body14m

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(32.dp)
                .background(color = categoryColor, shape = RoundedCornerShape(16.dp)),
        )

        Spacer(Modifier.width(8.dp))

        BasicTextField( // TODO: 추후 컴포넌트로 분리
            value = scheduleName,
            onValueChange = onNameChange,
            textStyle = textStyle,
            singleLine = true,
            decorationBox = { innerTextField ->
                if (scheduleName.isEmpty()) {
                    Text(
                        text = "일정...",
                        style = textStyle.copy(color = TogedyTheme.colors.gray300)
                    )
                }
                innerTextField()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ScheduleBottomSheetPreview(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TogedyTheme {
        ScheduleBottomSheet(
            scheduleId = null,
            sheetState = sheetState,
            onDismissRequest = {},
            modifier = modifier,
        )
    }
}
