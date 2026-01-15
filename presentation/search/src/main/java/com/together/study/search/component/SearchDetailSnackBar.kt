package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.R.drawable.ic_delete_24
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable


@Composable
fun SearchDetailSnackBar(
    modifier: Modifier = Modifier,
    admissionMethod: String,
    isAdded: Boolean,
    onUndoClick: () -> Unit = {},
) {
    val color = if (isAdded) TogedyTheme.colors.green else TogedyTheme.colors.red
    val icon = if (isAdded) ic_check_green else ic_delete_24
    val suffixText = if (isAdded) "을(를) 캘린더에 추가했어요" else "을(를) 캘린더에서 삭제했어요"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(
                color = TogedyTheme.colors.gray600,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = TogedyTheme.colors.white
            )
        }

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = TogedyTheme.typography.body13b.toSpanStyle().copy(
                        color = TogedyTheme.colors.white
                    )
                ) {
                    append(admissionMethod)
                }
                withStyle(
                    style = TogedyTheme.typography.toast13r.toSpanStyle().copy(
                        color = TogedyTheme.colors.white
                    )
                ) {
                    append(suffixText)
                }
            },
            style = TogedyTheme.typography.body13b.copy(
                lineBreak = LineBreak.Simple
            ),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )

        if (!isAdded) {
            Text(
                text = "되돌리기",
                style = TogedyTheme.typography.body13b.copy(
                    color = TogedyTheme.colors.white
                ),
                modifier = Modifier
                    .padding(start = 21.dp)
                    .noRippleClickable { onUndoClick() }
            )
        }
    }
}