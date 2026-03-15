package com.together.study.mypage.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.button.TogedyButton
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.component.FeedbackContent
import com.together.study.mypage.component.FeedbackTypeSelector
import com.together.study.mypage.component.MyTextField
import com.together.study.mypage.type.FeedbackType

@Composable
internal fun FeedbackRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    viewModel: FeedbackViewModel = hiltViewModel(),
) {
    FeedbackScreen(
        modifier = modifier,
        onBackButtonClick = onBackButtonClick,
        onSubmitClick = { type, content, email ->

        },
    )
}

@Composable
private fun FeedbackScreen(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onSubmitClick: (String, String, String?) -> Unit,
) {
    var content by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedTypeIdx by remember { mutableIntStateOf(-1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .padding(top = 14.dp),
    ) {
        TogedyTopBar(
            title = "문의하기",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            onLeftClicked = onBackButtonClick,
        )

        Spacer(Modifier.height(20.dp))

        FeedbackTypeSelector(
            typeList = FeedbackType.entries,
            selectedIndex = selectedTypeIdx,
            onSelectionChanged = { selectedTypeIdx = it },
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        FeedbackContent(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier.padding(horizontal = 16.dp),
        )


        Spacer(Modifier.height(24.dp))

        MyTextField(
            inputTitle = "답변을 받을 메일 주소를 입력해주세요!",
            content = {
                TogedyTextField(
                    value = email,
                    onValueChange = { email = it },
                    backgroundColor = TogedyTheme.colors.white,
                    placeholderText = "이메일을 입력해주세요",
                    showBorder = false,
                )
            },
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.weight(1f))

        TogedyButton(
            text = "제출하기",
            enabled = content.trim().isNotEmpty() && selectedTypeIdx != -1,
            onClick = {
                onSubmitClick(
                    FeedbackType.entries[selectedTypeIdx].serverValue,
                    content.trim(),
                    email,
                )
            },
            modifier = Modifier.padding(horizontal = 16.dp),
        )


        Spacer(Modifier.height(20.dp))
    }
}


