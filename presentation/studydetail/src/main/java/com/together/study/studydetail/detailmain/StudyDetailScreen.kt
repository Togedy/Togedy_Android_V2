package com.together.study.studydetail.detailmain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.ic_settings_24
import com.together.study.designsystem.R.drawable.ic_share_20
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.main.state.Study
import com.together.study.studydetail.detailmain.component.TextWithMoreButton

@Composable
internal fun StudyDetailRoute(modifier: Modifier = Modifier) {

}

@Composable
private fun StudyDetailScreen(
    isMyStudy: Boolean,
    study: Study,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(study.studyImageUrl)
                    .placeholder(img_study_background)
                    .error(img_study_background)
                    .fallback(img_study_background)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )

            StudyInfoSection(
                studyTag = study.studyTag,
                studyName = study.studyName,
                studyDescription = study.studyDescription,
                studyTier = study.studyTag,
                studyMemberCount = study.studyMemberCount,
                studyMemberLimit = study.studyMemberLimit,
                modifier = Modifier,
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.black)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_left_chevron),
            contentDescription = "뒤로 가기 버튼",
            tint = TogedyTheme.colors.white,
            modifier = Modifier.size(24.dp),
        )

        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(ic_share_20),
            contentDescription = "공유 버튼",
            tint = TogedyTheme.colors.white,
            modifier = Modifier.size(20.dp),
        )

        if (isMyStudy) {
            Spacer(Modifier.width(8.dp))

            Icon(
                imageVector = ImageVector.vectorResource(ic_settings_24),
                contentDescription = "설정 버튼",
                tint = TogedyTheme.colors.white,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
fun StudyInfoSection(
    studyTag: String,
    studyName: String,
    studyDescription: String,
    studyTier: String,
    studyMemberCount: Int,
    studyMemberLimit: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white)
            .padding(16.dp),
    ) {
        TogedyBasicTextChip(
            text = studyTag,
            textStyle = TogedyTheme.typography.body10m,
            textColor = TogedyTheme.colors.gray700,
            horizontalPadding = 6.dp,
            backgroundColor = TogedyTheme.colors.gray200,
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = studyName,
                style = TogedyTheme.typography.title18b,
                color = TogedyTheme.colors.black,
            )

            Spacer(Modifier.width(4.dp))

            if (studyTier.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            TogedyTheme.colors.gray200,
                            RoundedCornerShape(10.dp)
                        )
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TogedyTheme.colors.green)) {
                        append(studyMemberCount.toString())
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray500)) {
                        append("/$studyMemberLimit")
                    }
                },
                style = TogedyTheme.typography.body14m,
            )
        }

        Spacer(Modifier.height(12.dp))

        TextWithMoreButton(
            text = studyDescription,
        )
    }
}

@Preview
@Composable
private fun StudyDetailScreenPreview() {
    TogedyTheme {
        StudyDetailScreen(
            isMyStudy = true,
            study = Study.mock1,
            modifier = Modifier,
        )
    }
}
