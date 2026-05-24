package com.together.study.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.type.planner.toPlannerSubjectColorOrDefault
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.timer.model.SubjectTimer
import com.together.study.util.asColor

@Composable
internal fun SubjectTitle(
    subject: SubjectTimer,
    modifier: Modifier = Modifier,
    textColor: Color = TogedyTheme.colors.white,
) {
    val subjectColor = subject.subjectColor.toPlannerSubjectColorOrDefault().asColor()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(subjectColor, RoundedCornerShape(4.dp))
        )

        Spacer(Modifier.width(6.dp))

        Text(
            text = subject.subjectName,
            style = TogedyTheme.typography.body14b,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun SubjectTitlePreview() {
    TogedyTheme {
        SubjectTitle(
            subject = SubjectTimer(
                subjectId = 1,
                subjectName = "수학",
                subjectColor = "SUBJECT_COLOR1",
                studyTime = 0L,
            )
        )
    }
}
