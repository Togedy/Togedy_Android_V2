package com.together.study.designsystem.component.wheelpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import java.time.LocalDate

@Composable
fun TogedyYearMonthPicker(
    initYear: Int,
    initMonth: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int, Int) -> Unit,
) {
    var year by remember { mutableIntStateOf(initYear) }
    var month by remember { mutableIntStateOf(initMonth) }

    LaunchedEffect(year, month) {
        onValueChange(year, month)
    }

    Row(
        modifier = modifier
            .background(TogedyTheme.colors.white)
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TogedyScrollPicker(
            initValue = initYear,
            minValue = 2020,
            maxValue = LocalDate.now().year + 1,
            position = PickerPosition.START,
            unit = "년",
            modifier = Modifier.weight(1f),
            onValueChange = { year = it },
        )

        TogedyScrollPicker(
            initValue = initMonth,
            minValue = 1,
            maxValue = 12,
            position = PickerPosition.END,
            unit = "월",
            modifier = Modifier.weight(1f),
            onValueChange = { month = it },
        )
    }
}

@Preview
@Composable
private fun TogedyYearMonthPickerPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        TogedyYearMonthPicker(
            initYear = LocalDate.now().year,
            initMonth = LocalDate.now().monthValue,
            modifier = modifier,
            onValueChange = { year, month -> }
        )
    }
}
