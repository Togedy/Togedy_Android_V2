package com.together.study.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.calendar.model.Schedule
import com.together.study.calendar.type.toBackgroundColorOrDefault
import com.together.study.calendar.type.toCategoryColorOrDefault
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun UserScheduleItem(
    schedule: Schedule,
    onScheduleItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val categoryColor = schedule.category!!.categoryColor.toCategoryColorOrDefault()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .background(categoryColor.toBackgroundColorOrDefault(), RoundedCornerShape(6.dp))
            .padding(start = 6.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            .noRippleClickable(onScheduleItemClick),
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(categoryColor, RoundedCornerShape(10.dp)),
        )

        Spacer(Modifier.width(6.dp))

        Column {
            with(schedule) {
                category?.let {
                    Text(
                        text = scheduleName,
                        style = TogedyTheme.typography.chip14b.copy(categoryColor),
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = category!!.categoryName.toString(),
                        style = TogedyTheme.typography.body12m.copy(TogedyTheme.colors.gray400),
                    )
                }
            }
        }
    }
}
