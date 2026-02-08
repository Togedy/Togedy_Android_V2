package com.together.study.common.event

sealed interface TogedyUiEvent {
    data class ShowToast(
        val message: String,
        val icon: Int? = null,
    ) : TogedyUiEvent
}
