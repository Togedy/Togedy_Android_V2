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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    modifier: Modifier = Modifier,
    onNextClick: (String) -> Unit = {},
    onCheckNicknameDuplicate: (String) -> Boolean = { true },
) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var nicknameErrorMessage by rememberSaveable { mutableStateOf<String?>(null) }
    var isNicknameDuplicateChecked by rememberSaveable { mutableStateOf(false) }

    val isNextEnabled =
        isNicknameDuplicateChecked &&
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
            onValueChange = { changedNickname ->
                nickname = changedNickname.take(10)
                isNicknameDuplicateChecked = false
                nicknameErrorMessage = validateNickname(
                    nickname = nickname,
                    showBlankMessage = false,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "닉네임을 입력해주세요",
            backgroundColor = TogedyTheme.colors.white,
            showBorder = true,
            borderColor = if (isNicknameDuplicateChecked) {
                TogedyTheme.colors.green
            } else {
                TogedyTheme.colors.gray200
            },
            focusedBorderColor = if (isNicknameDuplicateChecked) {
                TogedyTheme.colors.green
            } else {
                TogedyTheme.colors.black
            },
            showDupCheck = true,
            dupCheckText = if (isNicknameDuplicateChecked) "확인완료" else "중복확인",
            onDupCheckClick = {
                val validationMessage = validateNickname(
                    nickname = nickname,
                    showBlankMessage = true,
                )
                if (validationMessage != null) {
                    nicknameErrorMessage = validationMessage
                    isNicknameDuplicateChecked = false
                } else if (!onCheckNicknameDuplicate(nickname)) {
                    nicknameErrorMessage = "이미 존재하는 닉네임입니다."
                    isNicknameDuplicateChecked = false
                } else {
                    nicknameErrorMessage = null
                    isNicknameDuplicateChecked = true
                }
            },
            isError = nicknameErrorMessage != null,
            errorMessage = nicknameErrorMessage,
        )

        if (nicknameErrorMessage == null && isNicknameDuplicateChecked) {
            NicknameDuplicateCheckedMessage()
        }

        Spacer(modifier = Modifier.weight(1f))

        TogedyButton(
            text = "다음",
            onClick = { onNextClick(nickname) },
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

private fun validateNickname(
    nickname: String,
    showBlankMessage: Boolean,
): String? {
    return when {
        nickname.isBlank() && showBlankMessage -> "닉네임을 입력해주세요"
        nickname.isNotBlank() && nickname.length !in 2..10 -> "2~10글자로 입력해주세요"
        else -> null
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
            OnboardingNicknameScreen()
        }
    }
}
