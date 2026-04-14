package com.together.study.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.together.study.designsystem.R.drawable.ic_kakao_logo
import com.together.study.presentation.login.R
import com.together.study.designsystem.component.toast.LocalTogedyToast
import com.together.study.designsystem.component.toast.ToastType
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.login.state.LoginUiEvent
import com.together.study.login.state.LoginUiState
import com.together.study.util.noRippleClickable
import timber.log.Timber

@Composable
internal fun LoginRoute(
    onNavigateToCalendar: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val togedyToast = LocalTogedyToast.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.NavigateToCalendar -> {
                    onNavigateToCalendar()
                }
                is LoginUiEvent.NavigateToOnboarding -> {
                    onNavigateToOnboarding()
                }
                is LoginUiEvent.ShowError -> {
                    togedyToast.makeText(
                        toastType = ToastType.COMMON,
                        message = event.message,
                    )
                }
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onKakaoLoginClick = {
            loginWithKakao(
                context = context,
                onSuccess = { token ->
                    viewModel.postLoginKakao(token)
                },
                onFailure = { error ->
                    togedyToast.makeText(
                        toastType = ToastType.COMMON,
                        message = error,
                    )
                }
            )
        },
        modifier = modifier,
    )
}

private fun loginWithKakao(
    context: Context,
    onSuccess: (String) -> Unit,
    onFailure: (String) -> Unit,
) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e(error, "카카오 로그인 실패")
            onFailure("카카오 로그인에 실패했습니다")
        } else if (token != null) {
            Timber.d("카카오 로그인 성공: ${token.accessToken}")
            onSuccess(token.accessToken)
        }
    }

    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Timber.e(error, "카카오톡으로 로그인 실패")

                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                Timber.d("카카오톡으로 로그인 성공: ${token.accessToken}")
                onSuccess(token.accessToken)
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}

@Composable
internal fun LoginScreen(
    uiState: LoginUiState,
    onKakaoLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = TogedyTheme.colors.white),
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxHeight(0.625f)
                .aspectRatio(1f)
                .drawBehind {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x4D00DF82),
                                Color.Transparent,
                            ),
                            center = center,
                            radius = size.minDimension / 2,
                        )
                    )
                },
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 83.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Togedy",
                style = TogedyTheme.typography.title24b.copy(
                    color = TogedyTheme.colors.green
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "입시의",
                style = TogedyTheme.typography.title24b.copy(
                    color = TogedyTheme.colors.black
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "모든 것을 함께해요",
                style = TogedyTheme.typography.title24b.copy(
                    color = TogedyTheme.colors.black
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(-(28).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.togedy_character_1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(165.dp)
                        .offset(y = 20.dp),
                    contentScale = ContentScale.Fit,
                )
                Image(
                    painter = painterResource(R.drawable.togedy_character_2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp),
                    contentScale = ContentScale.Fit,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = TogedyTheme.colors.yellow,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .then(
                        if (uiState is LoginUiState.Loading) {
                            Modifier
                        } else {
                            Modifier.noRippleClickable(onClick = onKakaoLoginClick)
                        }
                    )
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (uiState is LoginUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = TogedyTheme.colors.gray700,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_kakao_logo),
                        contentDescription = "카카오 로고 이미지",
                        tint = TogedyTheme.colors.gray700,
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = "카카오 로그인",
                        style = TogedyTheme.typography.body14b,
                        color = TogedyTheme.colors.gray700,
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
