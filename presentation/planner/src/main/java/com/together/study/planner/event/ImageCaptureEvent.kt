package com.together.study.planner.event

sealed class ImageCaptureEvent {
    data class CaptureSuccess(val message: String) : ImageCaptureEvent()
    data class ShowError(val message: String) : ImageCaptureEvent()
}
