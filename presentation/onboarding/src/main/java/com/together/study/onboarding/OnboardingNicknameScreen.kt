package com.together.study.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.component.button.TogedyButton
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun OnboardingNicknameScreen(
    nickname: String,
    isNicknameValidated: Boolean,
    nicknameErrorMessage: String?,
    isValidatingNickname: Boolean,
    onNicknameChange: (String) -> Unit,
    onValidateNickname: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isNextEnabled =
        isNicknameValidated &&
                nicknameErrorMessage == null &&
                nickname.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        Text(
            text = "닉네임을 설정해 주세요",
            style = TogedyTheme.typography.title18b.copy(
                color = TogedyTheme.colors.gray800
            ),
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "저희가 어떻게 불러드리면 될까요?",
            style = TogedyTheme.typography.body14r.copy(
                color = TogedyTheme.colors.gray500
            ),
        )

        Spacer(modifier = Modifier.height(19.dp))

        TogedyTextField(
            value = nickname,
            onValueChange = onNicknameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "닉네임을 입력해주세요",
            backgroundColor = TogedyTheme.colors.white,
            showBorder = true,
            borderColor = if (isNicknameValidated) {
                TogedyTheme.colors.green
            } else {
                TogedyTheme.colors.gray200
            },
            focusedBorderColor = if (isNicknameValidated) {
                TogedyTheme.colors.green
            } else {
                TogedyTheme.colors.black
            },
            showDupCheck = true,
            dupCheckText = when {
                isValidatingNickname -> "확인중..."
                isNicknameValidated -> "확인완료"
                else -> "중복확인"
            },
            onDupCheckClick = onValidateNickname,
            isError = nicknameErrorMessage != null,
            errorMessage = nicknameErrorMessage,
        )

        if (nicknameErrorMessage == null && isNicknameValidated) {
            NicknameDuplicateCheckedMessage()
        }

        Spacer(modifier = Modifier.weight(1f))

        TogedyButton(
            text = "다음",
            onClick = onNextClick,
            enabled = isNextEnabled,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun NicknameDuplicateCheckedMessage() {
    Row(
        modifier = Modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_check_green),
            contentDescription = null,
            tint = TogedyTheme.colors.green,
            modifier = Modifier.size(16.dp),
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "사용 가능한 닉네임입니다.",
            style = TogedyTheme.typography.body12m.copy(
                color = TogedyTheme.colors.green
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingNicknameScreenPreview() {
    TogedyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TogedyTheme.colors.white)
        ) {
            OnboardingNicknameScreen(
                nickname = "",
                isNicknameValidated = false,
                nicknameErrorMessage = null,
                isValidatingNickname = false,
                onNicknameChange = {},
                onValidateNickname = {},
                onNextClick = {},
            )
        }
    }
}
