package com.together.study.studymember.memberdetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.together.study.designsystem.theme.TogedyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberDetailBottomSheet(
    studyId: Long,
    memberId: Long,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        modifier = modifier.fillMaxWidth(),
        containerColor = TogedyTheme.colors.white,
    ) {
        MemberDetailSection(studyId = studyId, memberId = memberId)
    }
}

@Preview
@Composable
private fun MemberDetailBottomSheetPreview() {
    TogedyTheme {
        MemberDetailBottomSheet(
            studyId = 1,
            memberId = 1,
            onDismissRequest = {},
        )
    }
}
