package com.together.study.studysettings.subsettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.type.StudyRole

@Composable
fun MemberListScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MemberListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.memberState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .padding(top = 22.dp),
    ) {
        TogedyTopBar(
            title = "멤버 보기",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            modifier = Modifier.padding(bottom = 4.dp),
            onLeftClicked = onBackClick,
        )

        Spacer(Modifier.height(20.dp))

        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Empty -> {}
            is UiState.Failure -> {}
            is UiState.Success<*> -> {
                val memberList = (uiState as UiState.Success).data

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "멤버 ${memberList.size}명",
                        style = TogedyTheme.typography.body14b,
                        color = TogedyTheme.colors.gray800,
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = "최대 30명까지 가능해요",
                        style = TogedyTheme.typography.body10m,
                        color = TogedyTheme.colors.gray500,
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 26.dp),
                ) {
                    itemsIndexed(memberList) { index, item ->
                        when (item.studyRole) {
                            StudyRole.LEADER -> {
                                Box(
                                    modifier = Modifier,
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                TogedyTheme.colors.gray50,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = item.userName,
                                            style = TogedyTheme.typography.body13b,
                                            color = TogedyTheme.colors.gray700,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                        )

                                        TogedyBasicTextChip(
                                            text = "방장",
                                            textStyle = TogedyTheme.typography.body10m,
                                            textColor = TogedyTheme.colors.gray600,
                                            backgroundColor = TogedyTheme.colors.gray200,
                                            roundedCornerShape = RoundedCornerShape(4.dp),
                                            horizontalPadding = 4.dp,
                                            verticalPadding = 4.dp
                                        )
                                    }

                                    HorizontalDivider(
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        thickness = 1.dp,
                                        color = TogedyTheme.colors.gray200,
                                    )
                                }
                            }

                            StudyRole.MEMBER -> {
                                Box(
                                    modifier = Modifier,
                                    contentAlignment = Alignment.BottomCenter,
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = item.userName,
                                            style = TogedyTheme.typography.body13b,
                                            color = TogedyTheme.colors.gray700,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                        )
                                    }

                                    HorizontalDivider(
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        thickness = 1.dp,
                                        color = TogedyTheme.colors.gray200
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MemberListScreenPreview() {
    TogedyTheme {
        MemberListScreen(
            onBackClick = {},
        )
    }
}
