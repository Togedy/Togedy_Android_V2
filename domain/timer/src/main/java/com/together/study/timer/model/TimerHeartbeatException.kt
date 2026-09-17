package com.together.study.timer.model

sealed class TimerHeartbeatException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {

    /**
     * 서버 기준으로 유효하지 않은 타이머 오류 코드
     *
     * - 404 T14001 해당 타이머를 찾을 수 없습니다
     * - 409 T14003 이미 종료된 타이머입니다
     * - 403 T14002 해당 유저의 타이머가 아닙니다
     */
    class Invalidated(
        val statusCode: Int,
    ) : TimerHeartbeatException("서버에서 종료된 타이머 (status=$statusCode)")

    /**
     * 서버 기준 외 타이머 오류 코드
     *
     * - 유예 시간(150초) 안에 하트비트가 한 번이라도 성공하면 타이머 그대로 진행
     */
    class Transient(
        val statusCode: Int? = null,
        cause: Throwable? = null,
    ) : TimerHeartbeatException("하트비트 일시 실패 (status=$statusCode)", cause)
}
