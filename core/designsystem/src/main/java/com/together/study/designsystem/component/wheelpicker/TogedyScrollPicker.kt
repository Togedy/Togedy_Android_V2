package com.together.study.designsystem.component.wheelpicker

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import java.time.Year

/**
 * Vertical Scroll Picker
 *
 * @param initValue 초기 선택값
 * @param minValue 선택 범위 최소값
 * @param maxValue 선택 범위 최대값
 * @param position "start", "middle", "end" 로 box cornerShape를 결정
 * @param modifier 전체 박스 수정자
 * @param unit 단위 값 (생략 가능)
 */
@Composable
fun TogedyScrollPicker(
    initValue: Int,
    minValue: Int,
    maxValue: Int,
    position: String,
    modifier: Modifier = Modifier,
    unit: String = "",
) {
    val init by remember { mutableIntStateOf(initValue) }
    val listState = rememberLazyListState(init - minValue)

    val density = LocalDensity.current
    val threshold = remember { density.run { 16.dp.toPx() } }
    val selectedValue by remember { derivedStateOf { (listState.firstVisibleItemIndex + if (listState.firstVisibleItemScrollOffset >= threshold) minValue + 1 else minValue) } }

    val blockHeight = 32.dp
    val shapeValue = 12.dp
    val boxShape = when (position) {
        "start" -> RoundedCornerShape(topStart = shapeValue, bottomStart = shapeValue)
        "end" -> RoundedCornerShape(topEnd = shapeValue, bottomEnd = shapeValue)
        else -> RoundedCornerShape(0.dp)
    }

    Box(
        modifier = modifier
            .height(140.dp)
            .background(TogedyTheme.colors.white),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .background(color = TogedyTheme.colors.gray300, shape = boxShape)
                .fillMaxWidth()
                .height(blockHeight),
        )

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(0.dp, 50.dp),
            flingBehavior = rememberSnapFlingBehavior(listState),
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
        ) {
            items((minValue..maxValue).toList()) { value ->
                val textColor by animateColorAsState(
                    if (value == selectedValue) TogedyTheme.colors.black else TogedyTheme.colors.gray500,
                    label = "Color"
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(blockHeight),
                ) {
                    BasicText(
                        text = "$value$unit",
                        style = TogedyTheme.typography.title16sb.copy(textColor),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TogedyTimePickerPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        TogedyScrollPicker(
            initValue = Year.now().value,
            minValue = 2000,
            maxValue = Year.now().value + 1,
            unit = "년",
            position = "start",
            modifier = modifier,
        )
    }
}
