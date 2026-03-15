package com.together.study.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.button.TogedyButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.user.model.UserStudyInfo

@Composable
internal fun UserStudyInfoSection(
    studies: List<UserStudyInfo>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
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

                UserStudyItem(studyInfo = studyInfo)
            }
        }
    }
}
