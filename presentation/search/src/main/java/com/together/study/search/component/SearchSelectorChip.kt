package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.search.SearchScheduleData
import com.together.study.util.noRippleClickable

@Composable
fun SearchSelectorChip(
    data: SearchScheduleData,
    modifier: Modifier = Modifier,
    onSelectorClicked: () -> Unit = {}
) {
    val labels = listOf("원서접수", "서류제출", "합격자 발표")

    Card(
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
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
                .noRippleClickable(onSelectorClicked)
        ) {
            SearchSelectorHeader(
                admissionType = data.admissionType,
                universityName = data.universityName,
                isAdded = data.isAdded
            )

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
}