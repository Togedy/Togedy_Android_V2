package com.together.study.chatbot.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_arrow_left_24
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun ChatInputBar(
    value: String,
    placeholderText: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isSendAvailable: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val isSendEnabled = value.isNotBlank() && isSendAvailable
    val sendColor = if (isSendEnabled) TogedyTheme.colors.green else TogedyTheme.colors.greenBg

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = TogedyTheme.colors.white)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TogedyTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .background(
                    color = TogedyTheme.colors.gray50,
                    shape = RoundedCornerShape(20.dp)
                ),
            placeholderText = placeholderText,
            singleLine = false,
            showBorder = false,
            backgroundColor = Color.Transparent,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            textStyle = TogedyTheme.typography.body13m,
            textColor = TogedyTheme.colors.gray700,
            placeholderColor = TogedyTheme.colors.gray400,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = sendColor,
                    shape = RoundedCornerShape(100.dp)
                )
                .noRippleClickable {
                    if (isSendEnabled) {
                        onSendClick()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_left_24),
                modifier = Modifier
                    .size(20.dp)
                    .rotate(90f),
                tint = TogedyTheme.colors.white,
                contentDescription = null
            )
        }
    }
}
