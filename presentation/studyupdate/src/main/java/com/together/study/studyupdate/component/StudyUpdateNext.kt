package com.together.study.studyupdate.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun StudyUpdateNext(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "다음",
        style = TogedyTheme.typography.title16sb.copy(
            color = TogedyTheme.colors.white
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp)
            .then(
                if (isEnabled) {
                    Modifier.noRippleClickable(onClick)
                } else {
                    Modifier
                }
            )
            .background(
                color = if (isEnabled) TogedyTheme.colors.green else TogedyTheme.colors.gray400,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 16.dp),
        textAlign = TextAlign.Center
    )
}

