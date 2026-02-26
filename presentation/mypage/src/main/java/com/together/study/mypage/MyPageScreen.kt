package com.together.study.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_settings_24
import com.together.study.designsystem.component.button.TogedyButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.component.UserProfile
import com.together.study.mypage.component.UserStudyItem
import com.together.study.study.model.UserStudyInfo
import com.together.study.util.noRippleClickable


@Composable
internal fun MyPageRoute(
    onBackButtonClick: () -> Unit,
    onSettingNavigate: () -> Unit,
    modifier: Modifier = Modifier,
) {

    MyPageScreen(
        modifier = modifier,
        onSettingClick = onSettingNavigate,
    )
}

@Composable
private fun MyPageScreen(
    modifier: Modifier = Modifier,
    onSettingClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .padding(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "프로필",
                style = TogedyTheme.typography.title18b,
                color = TogedyTheme.colors.gray800,
            )

            Icon(
                imageVector = ImageVector.vectorResource(ic_settings_24),
                contentDescription = "뒤로가기",
                modifier = Modifier.noRippleClickable(onSettingClick),
            )
        }

        Spacer(Modifier.height(20.dp))

        UserProfile(
            userName = "유저입니당",
            userEmail = "user@gmail.com",
            userProfileImageUrl = "http://~~",
            totalStudyTime = "100:00:00",
            attendanceStreak = 4,
            onEditProfileClick = {},
        )

        Spacer(Modifier.height(12.dp))

        UserStudyInfo(
            studies = listOf(
                UserStudyInfo(
                    studyName = "토글디",
                    studyImageUrl = "http://~~",
                    studyMemberCount = 10,
                    completedMemberCount = 5,
                ),
                UserStudyInfo(
                    studyName = "토글디",
                    studyImageUrl = "http://~~",
                    studyMemberCount = 10,
                    completedMemberCount = 5,
                ),
            )
        )
    }
}

@Composable
fun UserStudyInfo(
    studies: List<UserStudyInfo>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white, RoundedCornerShape(16.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val comment =
                if (studies.isEmpty()) "기록 후 동기부여까지"
                else "전체보기"
            Text(
                text = "스터디",
                style = TogedyTheme.typography.chip14b,
                color = TogedyTheme.colors.gray800,
                modifier = Modifier.padding(vertical = 4.dp),
            )

            Text(
                text = comment,
                style = TogedyTheme.typography.toast13sb,
                color = TogedyTheme.colors.gray500,
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }

        if (studies.isEmpty()) {
            Spacer(Modifier.height(12.dp))

            TogedyButton(
                text = "스터디 시작하기",
                enabled = true,
                onClick = {},
                modifier = Modifier.height(50.dp),
            )
        } else {
            studies.forEach { studyInfo ->
                Spacer(Modifier.height(12.dp))

                UserStudyItem(
                    context = context,
                    studyInfo = studyInfo,
                )
            }
        }
    }
}



@Preview
@Composable
private fun MyPageRoutePreview() {
    TogedyTheme {
        MyPageScreen(
            onSettingClick = {},
        )
    }
}