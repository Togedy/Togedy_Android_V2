package com.together.study.mypage.state

import com.together.study.mypage.type.FeedbackType

data class FeedbackUiState(
    val type: FeedbackType? = null,
    val content: String = "",
    val replyEmail: String = "",
    val isDoneEnabled: Boolean = false,
)
