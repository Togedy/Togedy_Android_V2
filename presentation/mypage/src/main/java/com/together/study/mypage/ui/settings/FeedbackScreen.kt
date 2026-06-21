package com.together.study.mypage.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.button.TogedyButton
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.component.toast.LocalTogedyToast
import com.together.study.designsystem.component.toast.ToastType
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.component.FeedbackContent
import com.together.study.mypage.component.FeedbackTypeSelector
import com.together.study.mypage.component.MyTextField
import com.together.study.mypage.event.FeedbackEvent
import com.together.study.mypage.type.FeedbackType

@Composable
internal fun FeedbackRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    viewModel: FeedbackViewModel = hiltViewModel(),
) {
    val toast = LocalTogedyToast.current

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val eventFlow = viewModel.eventFlow

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is FeedbackEvent.PostFeedbackSuccess -> {
                    toast.makeText(
                        toastType = ToastType.COMMON,
                        message = "소중한 의견 감사드립니다 :)",
                        icon = ic_check_green,
                        yOffset = toast.toastBasicOffset(),
                    )
                    onBackButtonClick()
                }

                is FeedbackEvent.PostFeedbackFailure -> {
                    toast.makeText(
                        toastType = ToastType.WARNING,
                        message = event.message,
                        icon = ic_check_green,
                        yOffset = toast.toastOffsetWithBottomBar(),
                    )
                }
            }
        }
    }

    FeedbackScreen(
        type = uiState.value.type,
        content = uiState.value.content,
        email = uiState.value.replyEmail,
        isDoneEnabled = uiState.value.isDoneEnabled,
        modifier = modifier,
        onBackButtonClick = onBackButtonClick,
        onTypeChanged = viewModel::updateType,
        onContentChanged = viewModel::updateContent,
        onEmailChanged = viewModel::updateEmail,
        onSubmitClick = viewModel::postFeedback,
    )
}

@Composable
private fun FeedbackScreen(
    type: FeedbackType?,
    content: String,
    email: String,
    isDoneEnabled: Boolean,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onTypeChanged: (FeedbackType) -> Unit,
    onContentChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onSubmitClick: () -> Unit,
) {
    var selectedTypeIdx by remember { mutableIntStateOf(-1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .statusBarsPadding(),
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
            onSelectionChanged = { index, type ->
                selectedTypeIdx = index
                onTypeChanged(type)
            },
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        FeedbackContent(
            value = content,
            onValueChange = onContentChanged,
            modifier = Modifier.padding(horizontal = 16.dp),
        )


        Spacer(Modifier.height(24.dp))

        MyTextField(
            inputTitle = "답변을 받을 메일 주소를 입력해주세요!",
            content = {
                TogedyTextField(
                    value = email,
                    onValueChange = onEmailChanged,
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
            enabled = isDoneEnabled,
            onClick = onSubmitClick,
            modifier = Modifier.padding(horizontal = 16.dp),
        )


        Spacer(Modifier.height(20.dp))
    }
}


