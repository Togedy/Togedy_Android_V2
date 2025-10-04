package com.together.study.studydetail.detailmain

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.common.type.StudyType
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.ic_settings_24
import com.together.study.designsystem.R.drawable.ic_share_20
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.tabbar.StudyDetailTab
import com.together.study.designsystem.component.tabbar.TogedyTabBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.main.state.Study
import com.together.study.studydetail.detailmain.component.DailyCompletionBar
import com.together.study.studydetail.detailmain.component.StudyInfoSection
import com.together.study.util.noRippleClickable

@Composable
internal fun StudyDetailRoute(modifier: Modifier = Modifier) {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StudyDetailScreen(
    isMyStudy: Boolean,
    study: Study,
    selectedTab: StudyDetailTab,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onShareButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
    onTabChange: (StudyDetailTab) -> Unit,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            ) {
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
                    modifier = Modifier.fillMaxSize(),
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                        .drawWithContent {
                            drawContent()
                            val gradientHeight = size.height * 0.9f

                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Black, Color.Transparent),
                                    startY = 0f,
                                    endY = gradientHeight,
                                ),
                                topLeft = Offset.Zero,
                                size = Size(width = size.width, height = gradientHeight),
                            )
                        },
                )
            }

            StudyInfoSection(
                studyTag = study.studyTag,
                studyName = study.studyName,
                studyDescription = study.studyDescription,
                studyTier = study.studyTag,
                studyMemberCount = study.studyMemberCount,
                studyMemberLimit = study.studyMemberLimit,
                modifier = Modifier,
            )

            if (isMyStudy && study.completedMemberCount != 0 && study.studyType == StudyType.CHALLENGE.name) {
                DailyCompletionBar(
                    study.completedMemberCount,
                    study.studyMemberCount,
                )
            } else {
                HorizontalDivider(thickness = 8.dp, color = TogedyTheme.colors.gray50)
            }
        }

        stickyHeader {
            TogedyTabBar(
                tabList = StudyDetailTab.entries,
                selectedTab = selectedTab,
                onTabChange = onTabChange,
                modifier = Modifier,
            )
        }

        when (selectedTab) {
            StudyDetailTab.MEMBER -> TODO()
            StudyDetailTab.DAILY_CHECK -> TODO()
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
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable(onBackClick),
        )

        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(ic_share_20),
            contentDescription = "공유 버튼",
            tint = TogedyTheme.colors.white,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onShareButtonClick),
        )

        if (isMyStudy) {
            Spacer(Modifier.width(8.dp))

            Icon(
                imageVector = ImageVector.vectorResource(ic_settings_24),
                contentDescription = "설정 버튼",
                tint = TogedyTheme.colors.white,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable(onSettingsButtonClick),
            )
        }
    }
}

@Preview
@Composable
private fun StudyDetailScreenPreview() {
    TogedyTheme {
        StudyDetailScreen(
            isMyStudy = true,
            study = Study.mock1,
            selectedTab = StudyDetailTab.MEMBER,
            modifier = Modifier,
            onBackClick = {},
            onShareButtonClick = {},
            onSettingsButtonClick = {},
            onTabChange = {},
        )
    }
}
