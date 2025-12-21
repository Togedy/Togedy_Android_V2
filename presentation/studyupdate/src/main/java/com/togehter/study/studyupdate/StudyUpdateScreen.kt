package com.togehter.study.studyupdate

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.togehter.study.studyupdate.component.StudyTimeOption
import com.togehter.study.studyupdate.component.StudyUpdateImage
import com.togehter.study.studyupdate.component.StudyUpdateIntroduce
import com.togehter.study.studyupdate.component.StudyUpdateMemberCount
import com.togehter.study.studyupdate.component.StudyUpdateName
import com.togehter.study.studyupdate.component.StudyUpdateNext
import com.togehter.study.studyupdate.component.StudyUpdatePassword
import com.togehter.study.studyupdate.component.StudyUpdateTag
import com.togehter.study.studyupdate.component.StudyUpdateTime
import com.together.study.common.type.study.StudyUpdateType
import com.together.study.designsystem.R.drawable.ic_left_chevron_green
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun StudyUpdateRoute(
    onBackClick: () -> Unit,
    onNextClick: (Long, String, String, String?, String?, String, Int?, Boolean, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudyUpdateViewModel = hiltViewModel(),
) {
    val studyName by viewModel.studyName.collectAsState()
    val studyIntroduce by viewModel.studyIntroduce.collectAsState()
    val studyCategory by viewModel.studyCategory.collectAsState()
    val studyImage by viewModel.studyImage.collectAsState()
    val studyPassword by viewModel.studyPassword.collectAsState()
    val selectedMemberCount by viewModel.selectedMemberCount.collectAsState()
    val selectedStudyTime by viewModel.selectedStudyTime.collectAsState()
    val isChallenge by viewModel.isChallenge.collectAsState()
    val isNextButtonEnabled by viewModel.isNextButtonEnabled.collectAsState()
    val isStudyNameDuplicate by viewModel.isStudyNameDuplicate.collectAsState()
    val studyNameErrorMessage by viewModel.studyNameErrorMessage.collectAsState()
    val updateType = viewModel.updateType

    // 수정인 경우
    LaunchedEffect(Unit) {
        if (updateType == StudyUpdateType.UPDATE && viewModel.studyId != 0L) {
            viewModel.loadStudyDetailInfo()
        }
    }

    StudyUpdateScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        onNextClick = {
            val imageUriString = studyImage?.toString()
            onNextClick(
                viewModel.studyId,
                studyName,
                studyIntroduce,
                studyCategory,
                imageUriString,
                studyPassword,
                selectedMemberCount,
                isChallenge,
                selectedStudyTime.name
            )
        },
        type = updateType,
        isChallenge = isChallenge,
        studyName = studyName,
        studyIntroduce = studyIntroduce,
        studyCategory = studyCategory,
        studyImage = studyImage,
        studyPassword = studyPassword,
        selectedMemberCount = selectedMemberCount,
        selectedStudyTime = selectedStudyTime,
        isNextButtonEnabled = isNextButtonEnabled,
        onStudyNameChange = { viewModel.updateStudyName(it) },
        onStudyIntroduceChange = { viewModel.updateStudyIntroduce(it) },
        onStudyCategoryChange = { viewModel.updateStudyCategory(it) },
        onStudyImageChange = { viewModel.updateStudyImage(it) },
        onStudyPasswordChange = { viewModel.updateStudyPassword(it) },
        onSelectedMemberCountChange = { viewModel.updateSelectedMemberCount(it) },
        onSelectedStudyTimeChange = { viewModel.updateSelectedStudyTime(it) },
        onDupCheckClick = viewModel::checkStudyNameDuplicate,
        isStudyNameDuplicate = isStudyNameDuplicate == true,
        studyNameErrorMessage = studyNameErrorMessage,
    )
}


@Composable
fun StudyUpdateScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    type: StudyUpdateType,
    isChallenge: Boolean = false,
    studyName: String = "",
    studyIntroduce: String = "",
    studyCategory: String? = null,
    studyImage: Uri? = null,
    studyPassword: String = "",
    selectedMemberCount: Int? = null,
    selectedStudyTime: StudyTimeOption = StudyTimeOption.FIVE_HOURS,
    isNextButtonEnabled: Boolean = false,
    onStudyNameChange: (String) -> Unit = {},
    onStudyIntroduceChange: (String) -> Unit = {},
    onStudyCategoryChange: (String?) -> Unit = {},
    onStudyImageChange: (Uri?) -> Unit = {},
    onStudyPasswordChange: (String) -> Unit = {},
    onSelectedMemberCountChange: (Int?) -> Unit = {},
    onSelectedStudyTimeChange: (StudyTimeOption) -> Unit = {},
    onDupCheckClick: () -> Unit = {},
    isStudyNameDuplicate: Boolean = false,
    studyNameErrorMessage: String? = null,
) {
    val title = if (type == StudyUpdateType.CREATE) "스터디 생성" else "스터디 수정"

    // 스크롤 상태
    val listState = rememberLazyListState()
    LaunchedEffect(Unit) {
        listState.scrollToItem(0)
    }

    // 갤러리 이미지 선택 런처
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onStudyImageChange(uri)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = TogedyTheme.colors.gray50)
            .padding(horizontal = 16.dp)
    ) {
        TogedyTopBar(
            title = title,
            modifier = Modifier.padding(top = 23.dp),
            leftIcon = ImageVector.vectorResource(ic_left_chevron_green),
            leftIconColor = TogedyTheme.colors.gray800,
            onLeftClicked = onBackClick
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState
        ) {
            if (isChallenge) {
                item {
                    StudyUpdateTime(
                        selectedTime = selectedStudyTime,
                        onSelect = onSelectedStudyTimeChange,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            }

            item {
                StudyUpdateName(
                    value = studyName,
                    onValueChange = onStudyNameChange,
                    onDupCheckClick = onDupCheckClick,
                    isError = isStudyNameDuplicate,
                    errorMessage = studyNameErrorMessage
                )
            }

            item {
                StudyUpdateIntroduce(
                    value = studyIntroduce,
                    onValueChange = onStudyIntroduceChange
                )
            }

            item {
                StudyUpdateMemberCount(
                    selectedCount = selectedMemberCount,
                    onSelect = onSelectedMemberCountChange
                )
            }

            item {
                StudyUpdateTag(
                    selectedCategory = studyCategory,
                    onSelect = onStudyCategoryChange
                )
            }

            item {
                StudyUpdateImage(
                    imageUri = studyImage,
                    onImageClick = {
                        galleryLauncher.launch("image/*")
                    },
                    onDeleteClick = { onStudyImageChange(null) }
                )
            }

            item {
                StudyUpdatePassword(
                    value = studyPassword,
                    onValueChange = onStudyPasswordChange
                )
            }

            item {
                StudyUpdateNext(
                    isEnabled = isNextButtonEnabled,
                    onClick = {
                        onNextClick()
                    }
                )
            }
        }
    }
}

@Preview(name = "일반 스터디")
@Composable
fun StudyUpdateScreenPreview() {
    TogedyTheme {
        StudyUpdateScreen(
            type = StudyUpdateType.CREATE,
            isChallenge = false
        )
    }
}

@Preview(name = "챌린지 스터디")
@Composable
fun StudyUpdateScreenChallengePreview() {
    TogedyTheme {
        StudyUpdateScreen(
            type = StudyUpdateType.CREATE,
            isChallenge = true
        )
    }
}