package com.together.study.studymember.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.studyblock.StudyBlock
import com.together.study.designsystem.theme.TogedyTheme
import java.time.LocalDate

@Composable
internal fun StudyMonthlyColorBlock(
    currentDate: LocalDate,
    studyTimeList: List<Int>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(228.dp)
            .background(
                color = TogedyTheme.colors.gray50,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = currentDate.year.toString(),
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray500,
            )

            Text(
                text = "${currentDate.monthValue}월",
                style = TogedyTheme.typography.body10m,
                color = TogedyTheme.colors.gray800,
            )
        }

        StudyBlock(
            currentDate = currentDate,
            studyTimeList = studyTimeList,
        )
    }
}

@Preview
@Composable
private fun StudyMonthlyColorBlockPreview() {
    TogedyTheme {
        StudyMonthlyColorBlock(
            currentDate = LocalDate.now(),
            studyTimeList = emptyList(),
        )
    }
}
