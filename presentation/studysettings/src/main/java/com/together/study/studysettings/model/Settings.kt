package com.together.study.studysettings.model

import com.together.study.designsystem.R

data class Settings(
    val title: String,
    val subtitle: String? = null,
    val icon: Int? = R.drawable.ic_right_chevron_green,
    val isTextRed: Boolean = false,
    val onClick: () -> Unit,
)
