package com.together.study.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_add_24
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

/**
 * plus icon이 있는 추가 버튼 컴포넌트입니다.
 *
 * @param title 버튼 제목
 * @param onClick 버튼 클릭 시 실행되는 콜백함수
 * @param modifier 수정자
 */
@Composable
fun AddButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = TogedyTheme.colors.gray200,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_add_24),
            contentDescription = null,
            tint = TogedyTheme.colors.gray700,
            modifier = Modifier,
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = title,
            style = TogedyTheme.typography.body14m,
            color = TogedyTheme.colors.gray700,
        )
    }
}

@Preview
@Composable
private fun AddButtonPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        AddButton(
            title = "일정 추가",
            onClick = {},
            modifier = modifier,
        )
    }
}
