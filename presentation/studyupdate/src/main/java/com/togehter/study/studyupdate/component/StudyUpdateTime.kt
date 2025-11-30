package com.togehter.study.studyupdate.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

enum class StudyTimeOption(val displayName: String, val hours: Int) {
    FIVE_HOURS("5시간", 5),
    SEVEN_HOURS("7시간", 7),
    TEN_HOURS("10시간", 10)
}

@Composable
internal fun StudyUpdateTime(
    selectedTime: StudyTimeOption,
    onSelect: (StudyTimeOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = StudyTimeOption.entries

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = TogedyTheme.colors.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 5.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items.forEach { timeOption ->
            val isSelected = selectedTime == timeOption
            Box(
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = if (isSelected) 2.dp else 0.dp,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .background(
                        color = if (isSelected) TogedyTheme.colors.white else TogedyTheme.colors.gray100,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(vertical = 3.dp)
                    .noRippleClickable { onSelect(timeOption) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = timeOption.displayName,
                    style = TogedyTheme.typography.title16sb.copy(
                        color = if (isSelected) TogedyTheme.colors.green else TogedyTheme.colors.gray500
                    )
                )
            }
        }
    }
}