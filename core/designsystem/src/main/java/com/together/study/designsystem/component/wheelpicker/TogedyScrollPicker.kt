package com.together.study.designsystem.component.wheelpicker

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import java.time.Year

private val PickerHeight = 140.dp
private val ItemHeight = 32.dp
private val VerticalPadding = 50.dp

enum class PickerPosition {
    START, MIDDLE, END;

    fun toShape(corner: Dp): RoundedCornerShape = when (this) {
        START -> RoundedCornerShape(topStart = corner, bottomStart = corner)
        END -> RoundedCornerShape(topEnd = corner, bottomEnd = corner)
        MIDDLE -> RoundedCornerShape(0.dp)
    }
}

/**
 * Vertical Scroll Picker
 *
 * @param initValue 초기 선택값
 * @param minValue 선택 범위 최소값
 * @param maxValue 선택 범위 최대값
 * @param position "start", "middle", "end" 로 box cornerShape를 결정
 * @param modifier 전체 박스 수정자
 * @param unit 단위 값 (생략 가능)
 * @param isTimeValue 시간 관련 Picker인지 판단하는 값
 * @param onValueChange 선택된 값을 반환하는 콜백 함수
 */
@SuppressLint("DefaultLocale")
@Composable
fun TogedyScrollPicker(
    initValue: Int,
    minValue: Int,
    maxValue: Int,
    position: PickerPosition,
    modifier: Modifier = Modifier,
    unit: String = "",
    isTimeValue: Boolean = false,
    onValueChange: (Int) -> Unit = {},
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initValue - minValue)

    val density = LocalDensity.current
    val threshold = remember { density.run { 16.dp.toPx() } }

    val selectedValue by remember {
        derivedStateOf {
            val index = listState.firstVisibleItemIndex +
                    if (listState.firstVisibleItemScrollOffset >= threshold) 1 else 0
            (minValue + index).coerceIn(minValue, maxValue)
        }
    }

    LaunchedEffect(selectedValue) {
        onValueChange(selectedValue)
    }

    Box(
        modifier = modifier.height(PickerHeight),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = TogedyTheme.colors.gray300,
                    shape = position.toShape(12.dp)
                )
                .fillMaxWidth()
                .height(ItemHeight)
        )

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = VerticalPadding),
            flingBehavior = rememberSnapFlingBehavior(listState),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            items((minValue..maxValue).toList()) { value ->
                val isSelected = value == selectedValue
                val textColor by animateColorAsState(
                    if (isSelected) TogedyTheme.colors.black else TogedyTheme.colors.gray500,
                    label = "Color"
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ItemHeight)
                ) {
                    BasicText(
                        text = formatValue(value, isTimeValue) + unit,
                        style = TogedyTheme.typography.title16sb.copy(textColor),
                    )
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                TogedyTheme.colors.white,
                                Color.Transparent,
                            )
                        )
                    )
            )

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                TogedyTheme.colors.white,
                            )
                        )
                    )
            )
        }
    }
}

@SuppressLint("DefaultLocale")
private fun formatValue(value: Int, isTimeValue: Boolean): String {
    return if (isTimeValue) String.format("%02d", value) else value.toString()
}

@Preview
@Composable
private fun TogedyTimePickerPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        TogedyScrollPicker(
            initValue = Year.now().value,
            minValue = 2000,
            maxValue = Year.now().value + 1,
            unit = "년",
            position = PickerPosition.START,
            modifier = modifier,
        )
    }
}
