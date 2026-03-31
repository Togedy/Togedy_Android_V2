package com.together.study.mypage.event

sealed class FeedbackEvent {
    data object PostFeedbackSuccess : FeedbackEvent()
    data class PostFeedbackFailure(val message: String) : FeedbackEvent()
}