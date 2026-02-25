package com.together.study.timer.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject

@Composable
internal fun TimerSelectedSubject(
    subject: PlannerSubject,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .border(1.dp, textColor, RoundedCornerShape(40.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SubjectTitle(
            subject = subject,
            textColor = textColor,
            modifier = Modifier
                .padding(vertical = 6.dp)
                .padding(start = 12.dp),
        )

        Spacer(Modifier.width(2.dp))

        Icon(
            imageVector = ImageVector.vectorResource(ic_right_chevron_green),
            contentDescription = null,
            tint = TogedyTheme.colors.gray600,
            modifier = Modifier.padding(end = 4.dp),
        )
    }
}
