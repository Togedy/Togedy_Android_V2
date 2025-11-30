package com.together.study.studydetail.detailmain.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.together.study.studydetail.component.JoinStudyDialog
import com.together.study.studydetail.detailmain.UserInfoBottomSheet
import com.together.study.studydetail.detailmain.state.StudyDetailDialogState
import com.together.study.studydetail.detailmain.type.StudyDetailDialogType

@Composable
fun StudyDetailDialogScreen(
    studyId: Long,
    userId: Long,
    studyName: String,
    hasPassword: Boolean,
    errorMessage: String,
    dialogState: StudyDetailDialogState,
    modifier: Modifier = Modifier,
    onDismissRequest: (StudyDetailDialogType) -> Unit,
    onJoinStudyClick: (String?) -> Unit,
) {
    with(dialogState) {
        if (isJoinDialogVisible) {
            JoinStudyDialog(
                studyName = studyName,
                hasPassword = hasPassword,
                errorMessage = errorMessage,
                onDismissRequest = { onDismissRequest(StudyDetailDialogType.JOIN) },
                onJoinStudyClick = onJoinStudyClick,
                modifier = modifier,
            )
        }

        if (isJoinCompleteDialogVisible) {
            JoinCompleteDialog(
                modifier = modifier,
                onDismissRequest = { onDismissRequest(StudyDetailDialogType.JOIN_COMPLETE) }
            )
        }

        if (isUserBottomSheetVisible) {
            UserInfoBottomSheet(
                studyId = studyId,
                userId = userId,
                modifier = modifier,
                onDismissRequest = { onDismissRequest(StudyDetailDialogType.USER) },
            )
        }
    }
}
