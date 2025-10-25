package com.together.study.studydetail.detailmain.component

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.model.StudyMember
import com.together.study.study.type.StudyRole
import com.together.study.study.type.UserStatus
import com.together.study.util.noRippleClickable

@Composable
internal fun StudyMemberItem(
    context: Context,
    user: StudyMember,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
) {
    val isActive = user.userStatus == UserStatus.STUDYING
    val borderColor =
        if (isActive) TogedyTheme.colors.green
        else TogedyTheme.colors.gray400
    val textColor =
        if (isActive) TogedyTheme.colors.green
        else if (user.totalStudyAmount != null) TogedyTheme.colors.gray700
        else TogedyTheme.colors.gray500
    val statusText =
        if (isActive) "공부중"
        else user.totalStudyAmount ?: (user.lastActivatedAt ?: "")

    Column(
        modifier = modifier.noRippleClickable(onItemClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
                .padding(horizontal = 8.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(100.dp))
                .border(1.5.dp, borderColor, RoundedCornerShape(100.dp)),
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = user.userName,
            style = TogedyTheme.typography.body13b,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = statusText,
            style = TogedyTheme.typography.toast12r,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StudyMemberItemPreview() {
    val context = LocalContext.current
    TogedyTheme {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            StudyMemberItem(
                context = context,
                user = StudyMember(
                    userId = 1,
                    userName = "내가리더임",
                    studyRole = StudyRole.LEADER,
                    userStatus = UserStatus.ACTIVE,
                    userProfileImageUrl = "",
                    totalStudyAmount = "1H 20M",
                    lastActivatedAt = null,
                ),
                modifier = Modifier.weight(1f),
                onItemClick = {}
            )
            StudyMemberItem(
                context = context,
                user = StudyMember(
                    userId = 1,
                    userName = "투게디공부핑1투게디공부핑1투게디공부핑1",
                    studyRole = StudyRole.MEMBER,
                    userStatus = UserStatus.STUDYING,
                    userProfileImageUrl = "",
                    totalStudyAmount = null,
                    lastActivatedAt = "3분 전",
                ),
                modifier = Modifier.weight(1f),
                onItemClick = {}
            )
            StudyMemberItem(
                context = context,
                user = StudyMember(
                    userId = 1,
                    userName = "투게디공부핑2",
                    studyRole = StudyRole.MEMBER,
                    userStatus = UserStatus.ACTIVE,
                    userProfileImageUrl = "",
                    totalStudyAmount = null,
                    lastActivatedAt = "3분 전",
                ),
                modifier = Modifier.weight(1f),
                onItemClick = {}
            )
        }
    }
}
