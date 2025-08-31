package com.together.study.designsystem.component.wheelpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import java.time.LocalTime

@Composable
fun TogedyTimePicker(
    initHour: Int,
    initMinute: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int, Int) -> Unit,
) {
    var hour by remember { mutableIntStateOf(initHour) }
    var minute by remember { mutableIntStateOf(initMinute) }

    LaunchedEffect(hour, minute) {
        onValueChange(hour, minute)
    }

    Row(
        modifier = modifier
            .background(TogedyTheme.colors.white)
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TogedyScrollPicker(
            initValue = initHour,
            minValue = 0,
            maxValue = 23,
            position = PickerPosition.START,
            isTimeValue = true,
            modifier = Modifier.weight(1f),
            onValueChange = { hour = it },
        )

        Box(
            modifier = Modifier
                .background(TogedyTheme.colors.gray300)
                .height(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = ":",
                style = TogedyTheme.typography.title16sb
                    .copy(TogedyTheme.colors.black),
            )
        }

        TogedyScrollPicker(
            initValue = initMinute,
            minValue = 0,
            maxValue = 59,
            position = PickerPosition.END,
            isTimeValue = true,
            modifier = Modifier.weight(1f),
            onValueChange = { minute = it },
        )
    }
}

@Preview
@Composable
private fun TogedyTimePickerPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        TogedyTimePicker(
            initHour = LocalTime.now().hour,
            initMinute = 0,
            modifier = modifier,
            onValueChange = { hour, minute -> },
        )
    }
}
