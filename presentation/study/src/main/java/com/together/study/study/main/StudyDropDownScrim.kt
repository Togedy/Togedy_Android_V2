package com.together.study.study.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.together.study.designsystem.R.drawable.img_character_challenge
import com.together.study.designsystem.R.drawable.img_character_speaker_no_gradient
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

/**
 * 배경을 어둡게 하는 Scrim과 함께 표시되는 드롭다운 메뉴
 *
 * @param expanded 드롭다운 메뉴 표시 여부
 * @param onDismissRequest 메뉴를 닫을 때 호출
 * @param scrimAlpha 배경 어두움 정도
 * @param modifier 드롭다운 메뉴 Modifier
 * @param content 드롭다운 메뉴 내용
 */
@Composable
fun StudyDropDownScrim(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    scrimAlpha: Float = 0.4f,
    content: @Composable () -> Unit
) {
    if (expanded) {
        Popup(
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        TogedyTheme.colors.black.copy(
                            alpha = scrimAlpha
                        )
                    )
                    .noRippleClickable { onDismissRequest() }
            )
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .background(
                color = TogedyTheme.colors.white,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        content()
    }
}

@Composable
fun StudyDropDownScrimItem(
    text: String,
    imageResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(28.dp)
                )
                Spacer(Modifier.width(26.dp))
                Text(
                    text = text,
                    style = TogedyTheme.typography.body13b.copy(
                        color = TogedyTheme.colors.gray800
                    )
                )
            }
        },
        onClick = onClick,
        modifier = modifier
    )
}

@Preview()
@Composable
private fun StudyDropDownScrimPreview() {
    TogedyTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            StudyDropDownScrimItem(
                text = "스터디룸 만들기",
                imageResId = img_character_speaker_no_gradient,
                onClick = {}
            )
            StudyDropDownScrimItem(
                text = "챌린지 스터디룸 만들기",
                imageResId = img_character_challenge,
                onClick = {},
            )
        }
    }
}