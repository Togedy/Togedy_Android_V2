package com.together.study.studydetail.detailmain.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.together.study.study.main.state.Study
import com.together.study.studydetail.component.JoinStudyDialog
import com.together.study.studydetail.detailmain.state.StudyDetailDialogState
import com.together.study.studydetail.detailmain.type.StudyDetailDialogType

@Composable
fun StudyDetailDialogScreen(
    studyInfo: Study,
    dialogState: StudyDetailDialogState,
    modifier: Modifier = Modifier,
    onDismissRequest: (StudyDetailDialogType) -> Unit,
    onJoinStudyClick: () -> Unit,
) {
    with(dialogState) {
        if (isJoinDialogVisible) {
            with(studyInfo) {
                JoinStudyDialog(
                    studyName = studyName,
                    hasPassword = hasPassword,
                    password = password,
                    onDismissRequest = { onDismissRequest(StudyDetailDialogType.JOIN) },
                    onJoinStudyClick = onJoinStudyClick,
                    modifier = modifier,
                )
            }
        }

        if (isJoinCompleteDialogVisible) {
            JoinCompleteDialog(
                modifier = modifier,
                onDismissRequest = { onDismissRequest(StudyDetailDialogType.JOIN_COMPLETE) }
            )
        }

        if (isUserBottomSheetVisible) {
            //TODO: 유저 바텀시트
        }
    }
}
