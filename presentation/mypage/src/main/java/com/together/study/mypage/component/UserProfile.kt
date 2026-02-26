package com.together.study.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.designsystem.R.drawable.img_character_speaker_no_gradient
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun UserProfile(
    userName: String,
    userEmail: String,
    userProfileImageUrl: String,
    totalStudyTime: String,
    attendanceStreak: Int,
    modifier: Modifier = Modifier,
    onEditProfileClick: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white, RoundedCornerShape(16.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
            error = painterResource(img_character_speaker_no_gradient),
            placeholder = painterResource(img_character_speaker_no_gradient),
            fallback = painterResource(img_character_speaker_no_gradient),
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = userName,
            style = TogedyTheme.typography.title18b,
            color = TogedyTheme.colors.gray800,
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = userEmail,
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray500,
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            thickness = 1.dp,
            color = TogedyTheme.colors.gray200,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            UserRecordBlock(
                record = totalStudyTime,
                type = "누적 공부시간"
            )

            Spacer(Modifier.width(30.dp))

            UserRecordBlock(
                record = "$attendanceStreak",
                unit = "일",
                type = "연속 출석"
            )
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onEditProfileClick,
            enabled = true,
            modifier = modifier
                .fillMaxWidth()
                .border(1.dp, TogedyTheme.colors.gray200, RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = TogedyTheme.colors.white,
                contentColor = TogedyTheme.colors.gray800,
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = "프로필 수정",
                style = TogedyTheme.typography.chip14b,
                color = TogedyTheme.colors.gray800,
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }
    }
}

@Composable
fun UserRecordBlock(
    record: String,
    type: String,
    unit: String = "",
    modifier: Modifier = Modifier,
) {
    val recordColor =
        if (type == "연속 출석") TogedyTheme.colors.green
        else TogedyTheme.colors.gray800
    val icon =
        if (type == "연속 출석") "\uD83C\uDF89"
        else "🔥"


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = type,
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray500,
        )

        Text(
            text = icon + record + unit,
            style = TogedyTheme.typography.body14b,
            color = recordColor,
        )
    }
}
