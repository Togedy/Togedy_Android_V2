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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.type.study.StudyRole
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studysettings.type.MemberEditType
import com.together.study.util.noRippleClickable

data class Member(
    val userId: Long,
    val userName: String,
    val studyRole: StudyRole,
)

@Composable
fun MemberEditScreen(
    onBackClick: () -> Unit,
    type: MemberEditType = MemberEditType.EDIT,
    modifier: Modifier = Modifier,
) {
    var isMemberDialogVisible by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf(Member(0, "", StudyRole.MEMBER)) }

    val memberList = listOf(
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 1,
            userName = "member1",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 2,
            userName = "member2",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 3,
            userName = "member3",
            studyRole = StudyRole.MEMBER
        ),
        Member(
            userId = 4,
            userName = "member4",
            studyRole = StudyRole.MEMBER
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .padding(top = 22.dp),
    ) {
        TogedyTopBar(
            title = type.title,
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            modifier = Modifier.padding(bottom = 4.dp),
            onLeftClicked = onBackClick,
        )

        Spacer(Modifier.height(20.dp))

        if (type == MemberEditType.EDIT) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "스터디 멤버 관리 10/30",
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
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 26.dp),
        ) {
            item {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(TogedyTheme.colors.gray50, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "방장이름",
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

            itemsIndexed(memberList) { index, item ->
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
                            text = "member~!@",
                            style = TogedyTheme.typography.body13b,
                            color = TogedyTheme.colors.gray700,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )

                        if (!type.chipText.isNullOrBlank()) {
                            val textColor = when (type) {
                                MemberEditType.EDIT -> TogedyTheme.colors.red
                                MemberEditType.LEADER_CHANGE -> TogedyTheme.colors.green
                                else -> TogedyTheme.colors.gray600
                            }
                            val backgroundColor = when (type) {
                                MemberEditType.EDIT -> TogedyTheme.colors.red30
                                MemberEditType.LEADER_CHANGE -> TogedyTheme.colors.greenBg
                                else -> TogedyTheme.colors.gray600
                            }

                            TogedyBasicTextChip(
                                text = type.chipText,
                                textStyle = TogedyTheme.typography.body10m,
                                textColor = textColor,
                                backgroundColor = backgroundColor,
                                roundedCornerShape = RoundedCornerShape(4.dp),
                                horizontalPadding = 4.dp,
                                verticalPadding = 4.dp,
                                modifier = Modifier.noRippleClickable {
                                    selectedUser = item
                                    isMemberDialogVisible = true
                                }
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

    if (isMemberDialogVisible) {
        when (type) {
            MemberEditType.EDIT -> {
                TogedyBasicDialog(
                    title = "멤버 내보내기",
                    subTitle = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = TogedyTheme.typography.body14b.toSpanStyle()) {
                                    append(selectedUser.userName)
                                }
                                append("님을 내보낼까요?")
                            },
                            style = TogedyTheme.typography.body14m,
                            color = TogedyTheme.colors.gray900,
                            textAlign = TextAlign.Center,
                        )
                    },
                    buttonText = "내보내기",
                    onDismissRequest = { isMemberDialogVisible = false },
                    onButtonClick = { /* 추방 api */ }
                )
            }

            MemberEditType.LEADER_CHANGE -> {
                TogedyBasicDialog(
                    title = "방장 위임하기",
                    subTitle = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = TogedyTheme.typography.body14b.toSpanStyle()) {
                                    append(selectedUser.userName)
                                }
                                append("님을 방장으로 설정할까요?\n위임 후에는 일반 멤버로 변경됩니다.")
                            },
                            style = TogedyTheme.typography.body14m,
                            color = TogedyTheme.colors.gray900,
                            textAlign = TextAlign.Center,
                        )
                    },
                    buttonText = "설정하기",
                    onDismissRequest = { isMemberDialogVisible = false },
                    onButtonClick = { /* 위임 api */ }
                )
            }

            else -> {}
        }
    }
}

@Preview
@Composable
private fun MemberEditScreenPreview() {
    TogedyTheme {
        MemberEditScreen(
            onBackClick = {},
            type = MemberEditType.EDIT
        )
    }
}