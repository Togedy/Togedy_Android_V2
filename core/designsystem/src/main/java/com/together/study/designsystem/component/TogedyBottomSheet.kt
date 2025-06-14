package com.together.study.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TogedyBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    title: String = "",
    titleStyle: TextStyle =
        TogedyTheme.typography.title16sb.copy(
            color = TogedyTheme.colors.black
        ),
    showDone: Boolean = false,
    onDoneClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = TogedyTheme.colors.white,
        dragHandle = null
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.align(
                        alignment = Alignment.Companion.Center
                    ),
                    style = titleStyle
                )

                if (showDone) {
                    Text(
                        text = "완료",
                        modifier = Modifier
                            .align(alignment = Alignment.Companion.CenterEnd)
                            .noRippleClickable(onDoneClick)
                    )
                }
            }
            content()
        }
    }
}
