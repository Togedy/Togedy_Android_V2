package com.together.study.studysettings.main

import com.together.study.designsystem.R.drawable.ic_right_chevron_green

data class Settings(
    val title: String,
    val subtitle: String? = null,
    val icon: Int? = ic_right_chevron_green,
    val isTextRed: Boolean = false,
    val onClick: () -> Unit,
)
