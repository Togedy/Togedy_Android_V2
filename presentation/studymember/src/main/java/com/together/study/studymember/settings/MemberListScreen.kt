package com.together.study.studymember.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_leader
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.TogedySearchBar
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.type.StudyRole
import com.together.study.util.noRippleClickable

@Composable
fun MemberListScreen(
    onBackClick: () -> Unit,
    onMemberDetailNavigate: (Long, Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MemberListViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var searchTerm by remember { mutableStateOf("") }
    val uiState by viewModel.memberState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .padding(top = 22.dp),
    ) {
        TogedyTopBar(
            title = "스터디 멤버",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            modifier = Modifier.padding(bottom = 4.dp),
            onLeftClicked = onBackClick,
        )

        Spacer(Modifier.height(10.dp))

        TogedySearchBar(
            // TODO icon 추후 추가
            placeholder = "닉네임을 검색해보세요",
            value = searchTerm,
            onValueChange = { searchTerm = it },
            onSearchClicked = { },
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Empty -> {}
            is UiState.Failure -> {}
            is UiState.Success<*> -> {
                val memberList = (uiState as UiState.Success).data

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "멤버",
                        style = TogedyTheme.typography.body13m,
                        color = TogedyTheme.colors.gray800,
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = "${memberList.size}",
                        style = TogedyTheme.typography.body13m,
                        color = TogedyTheme.colors.green,
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 26.dp),
                ) {
                    itemsIndexed(memberList) { index, item ->
                        Box(
                            modifier = Modifier
                                .noRippleClickable {
                                    onMemberDetailNavigate(
                                        viewModel.studyId,
                                        item.userId
                                    )
                                },
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                AsyncImage(
                                    model = ImageRequest
                                        .Builder(context)
                                        .data("") // TODO DTO 수정 시 변경
                                        .placeholder(img_study_background)
                                        .error(img_study_background)
                                        .fallback(img_study_background)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(RoundedCornerShape(50.dp)),
                                )

                                Spacer(Modifier.width(12.dp))

                                Text(
                                    text = item.userName,
                                    style = TogedyTheme.typography.body13b,
                                    color = TogedyTheme.colors.gray700,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                )

                                if (item.studyRole == StudyRole.LEADER) {
                                    Spacer(Modifier.width(2.dp))

                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = ic_leader),
                                        contentDescription = null,
                                        tint = Color.Unspecified,
                                    )
                                }

                                if (item.userId == 1L) { // TODO DTO 수정 시 변경
                                    Spacer(Modifier.width(2.dp))

                                    TogedyBasicTextChip(
                                        text = "me",
                                        textStyle = TogedyTheme.typography.chip10sb,
                                        textColor = TogedyTheme.colors.green,
                                        backgroundColor = TogedyTheme.colors.greenBg,
                                    )
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                thickness = 1.dp,
                                color = TogedyTheme.colors.gray200
                            )
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
            onMemberDetailNavigate = { studyId, memberId -> },
        )
    }
}
