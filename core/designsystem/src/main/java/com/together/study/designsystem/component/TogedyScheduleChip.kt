package com.together.study.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun TogedyScheduleChip(
    typeStatus: Int,
    scheduleName: String,
    scheduleType: String,
    scheduleStartTime: String,
    scheduleEndTime: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = TogedyTheme.colors.gray100,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .height(intrinsicSize = IntrinsicSize.Min)
            .padding(all = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(3.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(
                            color = if (index < typeStatus)
                                TogedyTheme.colors.green
                            else TogedyTheme.colors.gray300,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .padding(vertical = 3.dp)
                .weight(weight = 1f)
        ) {
            Text(
                text = scheduleName,
                modifier = Modifier.padding(bottom = 4.dp),
                style = TogedyTheme.typography.chip14b.copy(
                    TogedyTheme.colors.green
                )
            )

            Text(
                text = scheduleType,
                modifier = Modifier.padding(bottom = 12.dp),
                style = TogedyTheme.typography.body12m.copy(
                    TogedyTheme.colors.gray400
                )
            )

            Row(
                modifier = Modifier
                    .background(
                        color = TogedyTheme.colors.gray200,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.img_clock_16),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 4.dp)
                )

                Text(
                    text = if (scheduleEndTime != null) {
                        "$scheduleStartTime - $scheduleEndTime"
                    } else {
                        scheduleStartTime
                    },
                    style = TogedyTheme.typography.body12m.copy(
                        TogedyTheme.colors.gray500
                    )
                )

            }
        }
    }

}