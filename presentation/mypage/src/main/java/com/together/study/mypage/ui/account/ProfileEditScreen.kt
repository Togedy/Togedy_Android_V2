package com.together.study.mypage.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_camera_24
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.img_character_heart
import com.together.study.designsystem.component.loading.TogedyLoadingScreen
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.component.toast.LocalTogedyToast
import com.together.study.designsystem.component.toast.ToastType
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.component.MyTextField
import com.together.study.mypage.event.ProfileEditEvent
import com.together.study.util.noRippleClickable

@Composable
internal fun ProfileEditRoute(
    onBackClick: () -> Unit,
    onGalleryNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileEditViewModel = hiltViewModel(),
) {
    val toast = LocalTogedyToast.current

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val eventFlow = viewModel.eventFlow

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is ProfileEditEvent.UpdateProfileSuccess -> onBackClick()
                is ProfileEditEvent.UpdateProfileFailure -> {
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

    when (uiState.value.profileState) {
        is UiState.Loading -> TogedyLoadingScreen()

        is UiState.Success<*> -> {
            val data = uiState.value

            ProfileEditScreen(
                userName = data.name,
                userProfileImageUrl = data.image ?: "",
                isError = data.isError,
                errorMessage = data.errorMessage,
                isDupCheck = data.isDupCheck,
                isEditBottomSheetVisible = data.isEditBottomSheetVisible,
                isDoneEnabled = data.isDoneEnabled,
                modifier = modifier,
                onBackClick = onBackClick,
                onDoneClick = viewModel::updateProfile,
                onEditBottomSheetStateChange = viewModel::setEditBottomSheetVisible,
                onImageDeleteButtonClick = viewModel::updateUserProfileImageUrl,
                onImageEditButtonClick = onGalleryNavigate,
                onNameChange = viewModel::updateUserName,
                onDupCheckClick = viewModel::checkDuplication,
            )
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileEditScreen(
    userName: String,
    userProfileImageUrl: String,
    isError: Boolean,
    errorMessage: String,
    isDupCheck: Boolean,
    isEditBottomSheetVisible: Boolean,
    isDoneEnabled: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    onEditBottomSheetStateChange: () -> Unit,
    onImageDeleteButtonClick: (String?) -> Unit,
    onImageEditButtonClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onDupCheckClick: () -> Unit,
) {
    val context = LocalContext.current
    val doneTextColor =
        if (isDoneEnabled) TogedyTheme.colors.green
        else TogedyTheme.colors.gray500

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TogedyTopBar(
            title = "프로필 수정",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            onLeftClicked = onBackClick,
            rightText = "완료",
            rightTextStyle = TogedyTheme.typography.title16sb.copy(color = doneTextColor),
            onRightClicked = { if (isDoneEnabled) onDoneClick() },
            modifier = Modifier.padding(vertical = 10.dp),
        )

        Spacer(Modifier.height(30.dp))

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.BottomEnd,
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(userProfileImageUrl)
                    .build(),
                contentDescription = "프로필 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(74.dp)
                    .border(1.dp, TogedyTheme.colors.gray200, RoundedCornerShape(50.dp))
                    .clip(RoundedCornerShape(50.dp)),
                error = painterResource(img_character_heart),
                placeholder = painterResource(img_character_heart),
                fallback = painterResource(img_character_heart),
            )

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(TogedyTheme.colors.white, RoundedCornerShape(50.dp))
                    .border(2.dp, TogedyTheme.colors.gray200, RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_camera_24),
                    contentDescription = "이미지 수정 버튼",
                    tint = Color.Unspecified,
                    modifier = Modifier.noRippleClickable(onEditBottomSheetStateChange),
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        MyTextField(
            inputTitle = "닉네임",
            inputEssential = true,
            inputTitleSub = "2~10글자",
            modifier = Modifier.padding(horizontal = 16.dp),
            content = {
                TogedyTextField(
                    value = userName,
                    onValueChange = onNameChange,
                    backgroundColor = TogedyTheme.colors.white,
                    placeholderText = "닉네임을 입력해주세요",
                    showBorder = true,
                    showDupCheck = true,
                    onDupCheckClick = onDupCheckClick,
                    isError = isError,
                    isPassed = isDupCheck,
                    errorMessage = errorMessage,
                )
            }
        )
    }

    if (isEditBottomSheetVisible) {
        ImageEditBottomSheet(
            onDismissRequest = onEditBottomSheetStateChange,
            onDeleteClick = {
                onImageDeleteButtonClick(null)
                onEditBottomSheetStateChange()
            },
            onEditClick = {
                onEditBottomSheetStateChange()
                onImageEditButtonClick()
            },
        )
    }
}