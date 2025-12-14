package com.togehter.study.studyupdate

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.togehter.study.studyupdate.component.StudyTimeOption
import com.together.study.designsystem.R.drawable.ic_left_chevron_green
import com.together.study.designsystem.R.drawable.img_character_done
import com.together.study.designsystem.R.drawable.img_no_image
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun StudyUpdateDoneRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    studyName: String = "",
    studyIntroduce: String = "",
    studyCategory: String? = null,
    studyImageUri: String? = null,
    studyPassword: String = "",
    memberCount: Int? = null,
    isChallenge: Boolean = false,
    selectedStudyTime: String = "FIVE_HOURS",
) {
    val studyImage = studyImageUri?.toUri()
    val studyTimeOption = runCatching { StudyTimeOption.valueOf(selectedStudyTime) }.getOrNull()
        ?: StudyTimeOption.FIVE_HOURS

    StudyUpdateDoneScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        studyName = studyName,
        studyIntroduce = studyIntroduce,
        studyCategory = studyCategory,
        studyPassword = studyPassword,
        studyImage = studyImage,
        memberCount = memberCount,
        isChallenge = isChallenge,
        selectedStudyTime = studyTimeOption
    )
}

@Composable
fun StudyUpdateDoneScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onStartClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    studyName: String = "",
    studyIntroduce: String = "",
    studyCategory: String? = null,
    studyPassword: String = "",
    studyImage: Uri? = null,
    memberCount: Int? = null,
    isChallenge: Boolean = false,
    selectedStudyTime: StudyTimeOption = StudyTimeOption.FIVE_HOURS
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = TogedyTheme.colors.gray50)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TogedyTopBar(
            title = "스터디 생성",
            modifier = Modifier.padding(top = 23.dp),
            leftIcon = ImageVector.vectorResource(ic_left_chevron_green),
            leftIconColor = TogedyTheme.colors.gray800,
            onLeftClicked = onBackClick
        )

        Image(
            painter = painterResource(img_character_done),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(top = 20.dp)

        )

        Text(
            text = "이대로 진행할까요?",
            style = TogedyTheme.typography.title18b.copy(
                color = TogedyTheme.colors.gray800
            )
        )

        Text(
            text = "스터디를 생성하기 전에 설정 내용을 확인해볼까요?",
            style = TogedyTheme.typography.toast12r.copy(
                color = TogedyTheme.colors.gray700
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 29.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = TogedyTheme.colors.gray200)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = TogedyTheme.colors.white,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                // 스터디 이미지
                if (studyImage != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(studyImage)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(84f / 320f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(4.dp))
                    )
                } else {
                    Image(
                        painter = painterResource(img_no_image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(84f / 320f)
                            .aspectRatio(1f)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // 카테고리
                        if (studyCategory != null) {
                            Text(
                                text = studyCategory,
                                style = TogedyTheme.typography.body10m.copy(
                                    color = TogedyTheme.colors.gray700
                                ),
                                modifier = Modifier
                                    .background(
                                        color = TogedyTheme.colors.gray200,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(vertical = 3.dp, horizontal = 6.dp)
                            )
                        }

                        Text(
                            text = "신규",
                            style = TogedyTheme.typography.body10m.copy(
                                color = TogedyTheme.colors.green
                            ),
                            modifier = Modifier
                                .background(
                                    color = TogedyTheme.colors.greenBg,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(vertical = 3.dp, horizontal = 6.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "방금 전",
                            style = TogedyTheme.typography.body10m.copy(
                                color = TogedyTheme.colors.green
                            ),
                        )
                    }

                    // 스터디 이름
                    Text(
                        text = studyName.ifEmpty { "스터디 이름" },
                        style = TogedyTheme.typography.body14m.copy(
                            color = TogedyTheme.colors.black
                        ),
                        modifier = Modifier.padding(top = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // 스터디 소개
                    Text(
                        text = studyIntroduce.ifEmpty { "스터디 소개" },
                        style = TogedyTheme.typography.body12m.copy(
                            color = TogedyTheme.colors.gray600
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    // 멤버 수
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(img_character_done),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(20f / 320f)
                                .aspectRatio(1f),
                        )

                        Text(
                            text = memberCount?.toString() ?: "-",
                            style = TogedyTheme.typography.body12m.copy(
                                color = TogedyTheme.colors.gray400
                            ),
                            modifier = Modifier.padding(start = 4.dp)
                        )

                        Text(
                            text = "/",
                            style = TogedyTheme.typography.body12m.copy(
                                color = TogedyTheme.colors.gray400
                            ),
                        )

                        Text(
                            text = "30",
                            style = TogedyTheme.typography.body12m.copy(
                                color = TogedyTheme.colors.black
                            ),
                            modifier = Modifier.padding(end = 3.dp),
                        )

                        if (isChallenge || studyPassword.isNotEmpty()) {
                            Spacer(
                                modifier = Modifier
                                    .width(1.dp)
                                    .background(color = TogedyTheme.colors.gray300)
                            )

                            if (isChallenge) {
                                Text(
                                    text = "매일",
                                    style = TogedyTheme.typography.body12m.copy(
                                        color = TogedyTheme.colors.gray500
                                    ),
                                    modifier = Modifier.padding(horizontal = 3.dp)
                                )

                                Box(
                                    modifier = Modifier
                                        .size(1.dp)
                                        .background(
                                            shape = RoundedCornerShape(100.dp),
                                            color = TogedyTheme.colors.gray500
                                        )
                                        .padding(start = 3.dp)
                                )

                                Text(
                                    text = selectedStudyTime.displayName,
                                    style = TogedyTheme.typography.body12m.copy(
                                        color = TogedyTheme.colors.gray500
                                    ),
                                    modifier = Modifier.padding(start = 3.dp)
                                )
                            }

                            if (studyPassword.isNotEmpty()) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(ic_left_chevron_green),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(start = 3.dp),
                                    tint = TogedyTheme.colors.gray500
                                )
                            }
                        }
                    }


                }
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = TogedyTheme.colors.gray50)
                .padding(vertical = 48.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "시작하기",
                style = TogedyTheme.typography.title16sb.copy(
                    color = TogedyTheme.colors.white
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable(onStartClick)
                    .background(
                        color = TogedyTheme.colors.green,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 11.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "수정할래요",
                style = TogedyTheme.typography.title16sb.copy(
                    color = TogedyTheme.colors.white
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable(onEditClick)
                    .background(
                        color = TogedyTheme.colors.gray400,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 11.dp),
                textAlign = TextAlign.Center
            )

        }

    }
}

@Preview
@Composable
fun StudyUpdateDoneScreenPreview() {
    TogedyTheme {
        StudyUpdateDoneScreen()
    }
}

