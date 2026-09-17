package com.together.study.timer.state

data class TimerServiceState(
    val elapsedTime: Int = 0,
    val isPlaying: Boolean = false,
    val isConnectionLost: Boolean = false, // 서버가 타이머를 종료한 경우
    val isHeartbeatUnstable: Boolean = false, // 하트비트 재시도 중인 경우
)