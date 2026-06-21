package com.together.study.timer.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.together.study.common.type.planner.toPlannerSubjectColorOrDefault
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.timer.model.SubjectTimer
import com.together.study.util.asColor
import com.together.study.util.noRippleClickable

private const val MAX_WIDTH_FRACTION = 220f / 360f

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TimerSelectedSubject(
    subject: SubjectTimer,
    modifier: Modifier = Modifier,
    onSubjectClick: () -> Unit,
) {
    val subjectColor = subject.subjectColor.toPlannerSubjectColorOrDefault().asColor()

    Text(
        text = subject.subjectName,
        style = TogedyTheme.typography.body14b,
        color = subjectColor,
        textAlign = TextAlign.Center,
        maxLines = 1,
        modifier = modifier
            .noRippleClickable(onSubjectClick)
            .fillMaxWidth(MAX_WIDTH_FRACTION)
            .basicMarquee(),
    )
}
