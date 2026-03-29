package com.together.study.onboarding

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.button.TogedyButton
import com.together.study.designsystem.component.textfield.TogedyTextField
import com.together.study.designsystem.theme.TogedyTheme
import java.time.LocalDate

@Composable
internal fun OnboardingBirthScreen(
    modifier: Modifier = Modifier,
    onNextClick: (LocalDate) -> Unit = {},
) {
    var birthYear by rememberSaveable { mutableStateOf("") }
    var birthMonth by rememberSaveable { mutableStateOf("") }
    var birthDay by rememberSaveable { mutableStateOf("") }

    val birthDate = parseBirthDate(
        yearText = birthYear,
        monthText = birthMonth,
        dayText = birthDay,
    )
    val birthDateErrorMessage = validateBirthDate(
        yearText = birthYear,
        monthText = birthMonth,
        dayText = birthDay,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        Text(
            text = "생년월일이 언제인가요?",
            style = TogedyTheme.typography.title18b.copy(
                color = TogedyTheme.colors.gray800
            ),
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "비슷한 연령대의 스터디 그룹이 우선 추천돼요",
            style = TogedyTheme.typography.body14r.copy(
                color = TogedyTheme.colors.gray500
            ),
        )

        Spacer(modifier = Modifier.height(19.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BirthDateTextField(
                value = birthYear,
                onValueChange = { birthYear = sanitizeNumberInput(it, 4) },
                placeholderText = "YYYY",
                modifier = Modifier.weight(1.4f),
                isError = birthDateErrorMessage != null,
            )

            Spacer(modifier = Modifier.width(8.dp))
            BirthDateUnitText(text = "년")
            Spacer(modifier = Modifier.width(8.dp))

            BirthDateTextField(
                value = birthMonth,
                onValueChange = { birthMonth = sanitizeNumberInput(it, 2) },
                placeholderText = "MM",
                modifier = Modifier.weight(1f),
                isError = birthDateErrorMessage != null,
            )

            Spacer(modifier = Modifier.width(8.dp))
            BirthDateUnitText(text = "월")
            Spacer(modifier = Modifier.width(8.dp))

            BirthDateTextField(
                value = birthDay,
                onValueChange = { birthDay = sanitizeNumberInput(it, 2) },
                placeholderText = "DD",
                modifier = Modifier.weight(1f),
                isError = birthDateErrorMessage != null,
            )

            Spacer(modifier = Modifier.width(8.dp))
            BirthDateUnitText(text = "일")
        }

        if (birthDateErrorMessage != null) {
            Text(
                text = birthDateErrorMessage,
                style = TogedyTheme.typography.body12m.copy(
                    color = TogedyTheme.colors.red
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TogedyButton(
            text = "가입 완료",
            onClick = {
                birthDate?.let(onNextClick)
            },
            enabled = birthDate != null,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun BirthDateUnitText(text: String) {
    Text(
        text = text,
        style = TogedyTheme.typography.body14m.copy(
            color = TogedyTheme.colors.gray700
        ),
    )
}

@Composable
private fun BirthDateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    TogedyTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholderText = placeholderText,
        backgroundColor = TogedyTheme.colors.white,
        showBorder = true,
        borderColor = if (isError) {
            TogedyTheme.colors.red
        } else {
            TogedyTheme.colors.gray200
        },
        focusedBorderColor = if (isError) {
            TogedyTheme.colors.red
        } else {
            TogedyTheme.colors.black
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
    )
}

private fun sanitizeNumberInput(
    value: String,
    maxLength: Int,
): String = value.filter(Char::isDigit).take(maxLength)

private fun parseBirthDate(
    yearText: String,
    monthText: String,
    dayText: String,
): LocalDate? {
    val year = yearText.toIntOrNull() ?: return null
    val month = monthText.toIntOrNull() ?: return null
    val day = dayText.toIntOrNull() ?: return null
    val currentDate = LocalDate.now()

    if (yearText.length != 4) return null
    if (year !in 1900..currentDate.year) return null
    if (month !in 1..12) return null

    return runCatching {
        LocalDate.of(year, month, day)
    }.getOrNull()?.takeIf { !it.isAfter(currentDate) }
}

private fun validateBirthDate(
    yearText: String,
    monthText: String,
    dayText: String,
): String? {
    if (yearText.isEmpty() && monthText.isEmpty() && dayText.isEmpty()) {
        return null
    }

    if (parseBirthDate(yearText, monthText, dayText) != null) {
        return null
    }

    val isAllFieldFilled =
        yearText.length == 4 &&
                monthText.isNotBlank() &&
                dayText.isNotBlank()

    return if (isAllFieldFilled) {
        "올바른 생년월일을 입력해주세요"
    } else {
        null
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingBirthScreenPreview() {
    TogedyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TogedyTheme.colors.white)
        ) {
            OnboardingBirthScreen()
        }
    }
}
