package com.together.study.study.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_search_24
import com.together.study.designsystem.component.tabbar.StudyMainTab
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.main.component.MainTabSection
import com.together.study.study.main.component.MyStudyItem
import com.together.study.study.main.component.TimerSection
import com.together.study.study.main.state.Study
import com.together.study.study.main.state.TimerInfo
import com.together.study.util.noRippleClickable

@Composable
fun StudyMainRoute(modifier: Modifier = Modifier) {
    StudyMainScreen(
        selectedTab = StudyMainTab.MAIN,
        userStudies = listOf(
            Study.mock1,
            Study.mock1,
            Study.mock1,
            Study.mock1,
            Study.mock1,
            Study.mock1
        ),
        modifier = modifier,
    )
}

@Composable
private fun StudyMainScreen(
    selectedTab: StudyMainTab,
    userStudies: List<Study>,
    modifier: Modifier = Modifier,
) {
    val mainColor =
        if (selectedTab == StudyMainTab.MAIN) TogedyTheme.colors.white
        else TogedyTheme.colors.black
    val subColor =
        if (selectedTab == StudyMainTab.MAIN) TogedyTheme.colors.gray700
        else TogedyTheme.colors.gray500
    val backgroundColor =
        if (selectedTab == StudyMainTab.MAIN) TogedyTheme.colors.black
        else TogedyTheme.colors.gray50
    val topSectionModifier = Modifier
        .fillMaxWidth()
        .background(backgroundColor)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray200),
        contentPadding = PaddingValues(bottom = 20.dp),
    ) {
        item {
            Spacer(topSectionModifier.height(22.dp))

            TitleSection(
                mainColor = mainColor,
                onSearchButtonClick = {},
                onCreateButtonClick = {},
                modifier = topSectionModifier,
            )

            Spacer(topSectionModifier.height(16.dp))

            MainTabSection(
                selectedTab = StudyMainTab.MAIN,
                mainColor = mainColor,
                subColor = subColor,
                backgroundColor = backgroundColor,
                onTabClick = {},
                modifier = topSectionModifier
            )
        }

        when (selectedTab) {
            StudyMainTab.MAIN -> {
                item {
                    TimerSection(TimerInfo.mock1)
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(TogedyTheme.colors.gray200)
                            .padding(16.dp),
                    ) {
                        Text(
                            text = "내 스터디",
                            style = TogedyTheme.typography.title16sb,
                            color = TogedyTheme.colors.gray800,
                        )
                    }
                }

                items(userStudies) { study ->
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    ) {
                        MyStudyItem(
                            study = study,
                            onItemClick = {}
                        )
                    }
                }
            }

            StudyMainTab.EXPLORE -> {}
            StudyMainTab.BADGE -> {
                TODO("2차 스프린트")
            }
        }

    }
}

@Composable
private fun TitleSection(
    mainColor: Color,
    onSearchButtonClick: () -> Unit,
    onCreateButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(start = 22.dp, end = 16.dp),
    ) {
        Text(text = "스터디", style = TogedyTheme.typography.title18b, color = mainColor)

        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(ic_search_24),
            contentDescription = "검색 버튼",
            tint = mainColor,
            modifier = Modifier.noRippleClickable(onSearchButtonClick),
        )

        Spacer(Modifier.width(16.dp))

        Icon(
            imageVector = ImageVector.vectorResource(ic_search_24),
            contentDescription = "스터디 생성 버튼",
            tint = mainColor,
            modifier = Modifier.noRippleClickable(onCreateButtonClick),
        )
    }
}

@Preview
@Composable
private fun StudyMainScreenPreview() {
    TogedyTheme {
        StudyMainScreen(
            selectedTab = StudyMainTab.MAIN,
            userStudies = listOf(Study.mock1),
            modifier = Modifier,
        )
    }
}
