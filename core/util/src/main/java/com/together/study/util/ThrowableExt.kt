package com.together.study.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.HttpException

fun Throwable.getHttpExceptionMessage(): String? = when (this) {
    is HttpException -> {
        val errorBody = response()?.errorBody()?.string()

        if (!errorBody.isNullOrBlank()) {
            runCatching {
                Json.decodeFromString<ApiErrorResponse>(errorBody).errorResponse?.message
            }.fold(
                onSuccess = { it },
                onFailure = { null },
            )
        } else null
    }

    else -> null
}

@Serializable
private data class ApiErrorResponse(
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("errorResponse")
    val errorResponse: ErrorDetail? = null,
)

@Serializable
private data class ErrorDetail(
    @SerialName("status")
    val status: Int,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
)