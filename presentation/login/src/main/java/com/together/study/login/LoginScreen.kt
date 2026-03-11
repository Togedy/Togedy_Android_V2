package com.together.study.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_kakao_logo
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    onDone: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .weight(3f)
                .background(TogedyTheme.colors.greenBg),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(TogedyTheme.colors.yellow, RoundedCornerShape(6.dp))
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_kakao_logo),
                contentDescription = "카카오 로고 이미지",
                tint = TogedyTheme.colors.gray700,
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = "카카오 로그인",
                style = TogedyTheme.typography.body14b,
                color = TogedyTheme.colors.gray700,
            )
        }

        Spacer(Modifier.weight(1f))
    }
}
