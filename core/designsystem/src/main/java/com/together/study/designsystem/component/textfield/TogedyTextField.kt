package com.together.study.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

/**
 * @param value 텍스트
 * @param onValueChange 텍스트 변경 호출 함수
 * @param modifier 텍스트 필드에 적용할 Modifier
 * @param textStyle 텍스트 스타일
 * @param textColor 텍스트 색상
 * @param placeholderText placeholder 텍스트
 * @param placeholderStyle placeholder 텍스트 스타일
 * @param placeholderColor placeholder 텍스트 색상
 * @param singleLine 텍스트 필드가 한 줄 제한 여부 (기본값 true)
 * @param shape 텍스트 필드의 모서리 (기본값 RoundedCornerShape(4.dp))
 * @param backgroundColor 텍스트 필드의 배경색
 * @param showBorder 테두리 표시 여부 (기본값 true)
 * @param borderColor 테두리 색상
 * @param focusedBorderColor 포커스 상태일 때 테두리 색상
 * @param contentPadding 텍스트 필드 내부 콘텐츠의 패딩 값
 * @param visualTransformation 텍스트 필드 암호화
 * @param enabled 텍스트 필드 사용 가능 여부 (기본값 true)
 * @param showDupCheck 중복 확인 버튼 표시 여부 (기본값 false)
 * @param dupCheckText 중복 확인 버튼에 표시될 텍스트 (기본값 "중복확인")
 * @param onDupCheckClick 중복 확인 버튼 클릭 호출 함수
 * @param minHeight 텍스트 필드의 최소 높이 (null일 경우 기본 높이)
 * @param isError 에러 상태 여부 (기본값 false)
 * @param errorMessage 텍스트 필드 하단에 표시될 에러 메시지 (null일 경우 표시 안 함)
 * @param errorMessageStyle 에러 메시지 텍스트의 스타일 (색상은 errorColor로 자동 적용됨)
 * @param errorMessagePadding 에러 메시지 패딩 값
 * @param errorColor 에러 상태일 때 테두리, 아이콘, 텍스트에 사용될 색상 (기본값: TogedyTheme.colors.red)
 * @param keyboardOptions 키보드 옵션 (기본값 KeyboardOptions.Default)
 */
@Composable
fun TogedyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TogedyTheme.typography.body14m,
    textColor: Color = TogedyTheme.colors.black,
    placeholderText: String = "",
    placeholderStyle: TextStyle = TogedyTheme.typography.body14m,
    placeholderColor: Color = TogedyTheme.colors.gray400,
    singleLine: Boolean = true,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = TogedyTheme.colors.gray50,
    showBorder: Boolean = true,
    borderColor: Color = TogedyTheme.colors.gray200,
    focusedBorderColor: Color = TogedyTheme.colors.black,
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    showDupCheck: Boolean = false,
    dupCheckText: String = "중복확인",
    onDupCheckClick: () -> Unit = {},
    minHeight: Dp? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    errorMessageStyle: TextStyle = TogedyTheme.typography.body12m,
    errorMessagePadding: PaddingValues = PaddingValues(top = 4.dp),
    errorColor: Color = TogedyTheme.colors.red,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var isPasswordVisible by remember { mutableStateOf(false) }

    // 비밀번호 표시 상태에 따른 visual transformation
    val effectiveVisualTransformation = remember(visualTransformation, isPasswordVisible) {
        if (visualTransformation != VisualTransformation.None && isPasswordVisible) {
            VisualTransformation.None
        } else {
            visualTransformation
        }
    }

    // Border color 계산 로직 통합
    val currentBorderColor =
        remember(isError, isFocused, errorColor, focusedBorderColor, borderColor) {
            when {
                isError -> errorColor
                isFocused -> focusedBorderColor
                else -> borderColor
            }
        }

    val textFieldModifier = modifier
        .background(backgroundColor, shape)
        .then(
            if (showBorder) {
                Modifier.border(
                    width = 1.dp,
                    color = currentBorderColor,
                    shape = shape
                )
            } else {
                Modifier
            }
        )
        .then(
            minHeight?.let { Modifier.defaultMinSize(minHeight = it) } ?: Modifier
        )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = textStyle.copy(color = textColor),
        visualTransformation = effectiveVisualTransformation,
        enabled = enabled,
        interactionSource = interactionSource,
        cursorBrush = Brush.verticalGradient(listOf(textColor, textColor)),
        keyboardOptions = keyboardOptions,
        modifier = textFieldModifier,
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                // 메인 입력 필드 Row
                TextFieldContent(
                    value = value,
                    placeholderText = placeholderText,
                    placeholderStyle = placeholderStyle,
                    placeholderColor = placeholderColor,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    showDupCheck = showDupCheck,
                    dupCheckText = dupCheckText,
                    onDupCheckClick = onDupCheckClick,
                    enabled = enabled,
                    backgroundColor = backgroundColor,
                    borderColor = currentBorderColor,
                    onPasswordVisibilityChange = { isPasswordVisible = it },
                    innerTextField = innerTextField
                )

                // 에러 메시지 표시
                if (errorMessage != null) {
                    ErrorMessageRow(
                        errorMessage = errorMessage,
                        errorMessageStyle = errorMessageStyle,
                        errorMessagePadding = errorMessagePadding,
                        errorColor = errorColor
                    )
                }
            }
        }
    )
}

@Composable
private fun TextFieldContent(
    value: String,
    placeholderText: String,
    placeholderStyle: TextStyle,
    placeholderColor: Color,
    singleLine: Boolean,
    visualTransformation: VisualTransformation,
    showDupCheck: Boolean,
    dupCheckText: String,
    onDupCheckClick: () -> Unit,
    enabled: Boolean,
    backgroundColor: Color,
    borderColor: Color,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    innerTextField: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top
    ) {
        // 입력 필드
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholderText,
                    style = placeholderStyle.copy(color = placeholderColor),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            innerTextField()
        }

        // 비밀번호 표시/숨김 아이콘
        if (visualTransformation != VisualTransformation.None) {
            Spacer(Modifier.width(8.dp))
            PasswordVisibilityIcon(
                onVisibilityChange = onPasswordVisibilityChange
            )
        }

        // 중복 확인 버튼
        if (showDupCheck) {
            Spacer(Modifier.width(8.dp))
            DupCheckButton(
                text = dupCheckText,
                onClick = onDupCheckClick,
                enabled = enabled,
                backgroundColor = backgroundColor,
                borderColor = borderColor
            )
        }
    }
}

@Composable
private fun PasswordVisibilityIcon(
    onVisibilityChange: (Boolean) -> Unit
) {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_eye),
        contentDescription = "비밀번호 표시",
        tint = TogedyTheme.colors.gray400,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    onVisibilityChange(true)
                    tryAwaitRelease()
                    onVisibilityChange(false)
                }
            )
        }
    )
}

@Composable
private fun DupCheckButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    backgroundColor: Color,
    borderColor: Color
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .noRippleClickable(onClick)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = if (enabled) Color.Transparent else backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TogedyTheme.typography.body12m.copy(color = TogedyTheme.colors.black),
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}

@Composable
private fun ErrorMessageRow(
    errorMessage: String,
    errorMessageStyle: TextStyle,
    errorMessagePadding: PaddingValues,
    errorColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(errorMessagePadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_red),
            contentDescription = "에러",
            tint = errorColor,
            modifier = Modifier.padding(end = 4.dp)
        )

        Text(
            text = errorMessage,
            style = errorMessageStyle.copy(color = errorColor)
        )
    }
}

// Preview
@Preview
@Composable
fun TogedyTextFieldPreview_Default() {
    TogedyTheme {
        var text by remember { mutableStateOf("") }
        TogedyTextField(
            value = text,
            onValueChange = { text = it },
            placeholderText = "이름을 입력해주세요 (최대 nn자)"
        )
    }
}

@Preview
@Composable
fun TogedyTextFieldPreview_ErrorState() {
    TogedyTheme {
        var text by remember { mutableStateOf("욕설") }
        TogedyTextField(
            value = text,
            onValueChange = { text = it },
            placeholderText = "내용을 입력하세요",
            isError = true,
            errorMessage = "욕설/비방 글은 작성이 불가합니다"
        )
    }
}

@Preview
@Composable
fun TogedyTextFieldPreview_Password() {
    TogedyTheme {
        var text by remember { mutableStateOf("") }
        TogedyTextField(
            value = text,
            onValueChange = { text = it },
            placeholderText = "비밀번호",
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Preview
@Composable
fun TogedyTextFieldPreview_DupCheck() {
    TogedyTheme {
        var text by remember { mutableStateOf("") }
        TogedyTextField(
            value = text,
            onValueChange = { text = it },
            placeholderText = "아이디",
            showDupCheck = true,
            onDupCheckClick = { /* 중복 확인 로직 */ }
        )
    }
}