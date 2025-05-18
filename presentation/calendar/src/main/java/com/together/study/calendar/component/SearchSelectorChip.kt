package com.together.study.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.calendar.SearchScheduleData
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun SearchSelectorChip(
    data: SearchScheduleData,
    modifier: Modifier = Modifier,
    onSelectorClicked: () -> Unit = {}
) {
    val labels = listOf("원서접수", "서류제출", "합격자 발표")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = TogedyTheme.colors.white,
                shape = RoundedCornerShape(6.dp)
            )
            .then(
                if (data.isAdded) Modifier
                    .border(
                        width = 1.dp,
                        color = TogedyTheme.colors.green,
                        shape = RoundedCornerShape(6.dp)
                    ) else Modifier
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .noRippleClickable {
                onSelectorClicked()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.admissionType,
                style = TogedyTheme.typography.chip10sb.copy(
                    color = if (data.isAdded)
                        TogedyTheme.colors.gray700
                    else
                        TogedyTheme.colors.white
                ),
                modifier = Modifier
                    .background(
                        color = if (data.isAdded)
                            TogedyTheme.colors.gray100
                        else
                            TogedyTheme.colors.black,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )


            Text(
                text = data.universityName,
                style = TogedyTheme.typography.title16sb.copy(
                    color = TogedyTheme.colors.green
                ),
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "캘린더 삭제",
                style = TogedyTheme.typography.body12m.copy(
                    color = TogedyTheme.colors.green
                ),
                modifier = Modifier
                    .background(
                        color = if (data.isAdded)
                            TogedyTheme.colors.white
                        else
                            TogedyTheme.colors.greenBg,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .then(
                        if (data.isAdded) Modifier.border(
                            width = 1.dp,
                            color = TogedyTheme.colors.green,
                            shape = RoundedCornerShape(4.dp)
                        ) else Modifier
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

        }

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        labels.forEach { label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = TogedyTheme.typography.body12m.copy(
                        color = TogedyTheme.colors.gray600
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "전형별 상이",
                    style = TogedyTheme.typography.body12m.copy(
                        color = TogedyTheme.colors.gray500
                    ),
                    modifier = Modifier
                        .background(color = TogedyTheme.colors.gray100)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                )
            }
        }
    }
}