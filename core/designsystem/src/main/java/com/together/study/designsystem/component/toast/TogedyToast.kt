package com.together.study.designsystem.component.toast

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.together.study.designsystem.theme.TogedyTheme

val LocalTogedyToast = staticCompositionLocalOf<TogedyToast> {
    error("no TogedyToast instance provided")
}

@Immutable
class TogedyToast(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
) : Toast(context) {
    /**
     * 투게디의 커스텀 토스트 메시지를 출력합니다.
     *
     * @param toastType 토스트 메시지의 종류 (Common, Warning)
     * @param message 내용
     * @param icon 아이콘
     * @param [yOffset] 화면 하단과 토스트 메시지간 간격 설정값
     */
    fun makeText(
        toastType: ToastType,
        message: String,
        icon: Int? = null,
        yOffset: Int = 0,
    ) {
        when (toastType) {
            ToastType.COMMON -> showCommonToast(
                message = message,
                icon = icon,
                yOffset = yOffset,
            )

            ToastType.WARNING -> showWarningToast(
                message = message,
                yOffset = yOffset,
            )
        }
    }

    private fun showCommonToast(
        message: String,
        icon: Int?,
        yOffset: Int,
    ) = setView(yOffset = yOffset) {
        CommonToast(
            message = message,
            icon = icon?.let { ImageVector.vectorResource(icon) },
            modifier = Modifier.fillMaxWidth(),
        )
    }

    private fun showWarningToast(
        message: String,
        yOffset: Int,
    ) = setView(yOffset = yOffset) {
        WarningToast(
            message = message,
            modifier = Modifier.fillMaxWidth(),
        )
    }

    private fun setView(
        yOffset: Int = 0,
        content: @Composable () -> Unit,
    ) {
        val views = ComposeView(context)

        views.setContent {
            TogedyTheme {
                content()
            }
        }

        views.setViewTreeLifecycleOwner(lifecycleOwner)
        views.setViewTreeSavedStateRegistryOwner(lifecycleOwner as? SavedStateRegistryOwner)
        views.setViewTreeViewModelStoreOwner(lifecycleOwner as? ViewModelStoreOwner)

        this.duration = LENGTH_SHORT
        this.view = views

        this.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, yOffset)
        this.show()
    }

    fun toastOffsetWithBottomBar(): Int {
        return with(Density(context)) { 100.dp.toPx() }.toInt()
    }
}