package com.together.study.studydetail.detailmain.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.tabbar.StudyMemberTab
import com.together.study.designsystem.component.tabbar.TogedyTabBar
import com.together.study.designsystem.theme.TogedyTheme

data class StudyMemberProfile(
    val userName: String,
    val userStatus: String,
    val userProfileImageUrl: String,
    val userProfileMessage: String,
    val totalStudyTime: String,
    val attendanceStreak: Int,
    val elapsedDays: Int,
) {
    companion object {
        val mock = StudyMemberProfile(
            "유저",
            "ACTIVE",
            "",
            "~~~",
            "100:00:00",
            4,
            15,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserInfoBottomSheet(
    user: StudyMemberProfile,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .background(TogedyTheme.colors.white),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(12.dp))

            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(user.userProfileImageUrl)
                    .placeholder(img_study_background)
                    .error(img_study_background)
                    .fallback(img_study_background)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(50.dp)),
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = user.userName,
                style = TogedyTheme.typography.title18b,
                color = TogedyTheme.colors.gray900,
            )

            Spacer(Modifier.height(20.dp))

            TogedyTabBar(
                tabList = StudyMemberTab.entries,
                selectedTab = StudyMemberTab.STUDY_TIME,
                onTabChange = {},
            )
        }
    }
}

@Preview
@Composable
private fun UserInfoBottomSheetPreview() {
    TogedyTheme {
        UserInfoBottomSheet(
            user = StudyMemberProfile.mock,
            onDismissRequest = {},
        )
    }
}
