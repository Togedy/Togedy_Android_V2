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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.togehter.study.studyupdate.type.StudyUpdateType
import com.together.study.designsystem.R.drawable.ic_left_chevron_green
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun StudyUpdateRoute(
    onBackClick: () -> Unit,
    onNextClick: (String, String, String?, Uri?, Int?, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudyUpdateViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
    }

    StudyUpdateScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        onNextClick = onNextClick,
        type = StudyUpdateType.CREATE,
        isChallenge = viewModel.isChallenge,
    )
}


@Composable
fun StudyUpdateScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNextClick: (String, String, String?, Uri?, Int?, Boolean) -> Unit = { _, _, _, _, _, _ -> },
    type: StudyUpdateType,
    isChallenge: Boolean = false,
) {
    val title = if (type == StudyUpdateType.CREATE) "스터디 생성" else "스터디 수정"

    // 상태 관리
    var studyName by remember { mutableStateOf("") }
    var studyIntroduce by remember { mutableStateOf("") }
    var studyCategory by remember { mutableStateOf<String?>(null) }
    var studyImage by remember { mutableStateOf<Uri?>(null) }
    var studyPassWord by remember { mutableStateOf("") }
    var selectedMemberCount by remember { mutableStateOf<Int?>(null) }
    var selectedStudyTime by remember { mutableStateOf(StudyTimeOption.FIVE_HOURS) }

    // 갤러리 이미지 선택 런처
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { studyImage = it }
    }

    // 필수 태그 만족 요건
    val isNextButtonEnabled =
        remember(studyName, studyIntroduce, selectedMemberCount, studyCategory) {
            studyName.isNotBlank() &&
                    studyIntroduce.isNotBlank() &&
                    selectedMemberCount != null &&
                    studyCategory != null
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
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isChallenge) {
                item {
                    StudyUpdateTime(
                        selectedTime = selectedStudyTime,
                        onSelect = { selectedStudyTime = it },
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            }

            item {
                StudyUpdateName(
                    value = studyName,
                    onValueChange = { studyName = it },
                    onDupCheckClick = {
                        // 중복 확인 로직
                    }
                )
            }

            item {
                StudyUpdateIntroduce(
                    value = studyIntroduce,
                    onValueChange = { studyIntroduce = it }
                )
            }

            item {
                StudyUpdateMemberCount(
                    selectedCount = selectedMemberCount,
                    onSelect = { selectedMemberCount = it }
                )
            }

            item {
                StudyUpdateTag(
                    selectedCategory = studyCategory,
                    onSelect = { studyCategory = it }
                )
            }

            item {
                StudyUpdateImage(
                    imageUri = studyImage,
                    onImageClick = {
                        galleryLauncher.launch("image/*")
                    },
                    onDeleteClick = { studyImage = null }
                )
            }

            item {
                StudyUpdatePassword(
                    value = studyPassWord,
                    onValueChange = { studyPassWord = it }
                )
            }

            item {
                StudyUpdateNext(
                    isEnabled = isNextButtonEnabled,
                    onClick = {
                        onNextClick(
                            studyName,
                            studyIntroduce,
                            studyCategory,
                            studyImage,
                            selectedMemberCount,
                            isChallenge
                        )
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