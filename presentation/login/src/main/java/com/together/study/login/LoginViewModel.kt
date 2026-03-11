package com.together.study.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.auth.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
) : ViewModel() {

    fun loginWithKakao(token: String) {
        viewModelScope.launch {
            kakaoLoginUseCase(token)
                .onSuccess { response ->
//                    runCatching {
//                        saveTokensUseCase(
//                            accessToken = response.accessToken,
//                            refreshToken = response.refreshToken,
//                        )
//                    }


                }
                .onFailure { e ->

                }
        }
    }
}