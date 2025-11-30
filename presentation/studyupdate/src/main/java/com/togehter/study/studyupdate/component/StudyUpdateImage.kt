package com.togehter.study.studyupdate.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.togehter.study.studyupdate.StudyUpdateTextItem
import com.together.study.designsystem.R.drawable.ic_no_image
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun StudyUpdateImage(
    imageUri: Uri?,
    onImageClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    StudyUpdateTextItem(
        modifier = modifier.padding(top = 32.dp),
        inputTitle = "스터디 대표 이미지",
        inputEssential = false,
        isDelete = imageUri != null,
        onDeleteClicked = onDeleteClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = TogedyTheme.colors.white)
                .border(
                    width = 1.dp,
                    color = TogedyTheme.colors.gray300,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .noRippleClickable(onClick = onImageClick)
        ) {
            if (imageUri == null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_no_image),
                        contentDescription = null,
                        tint = TogedyTheme.colors.gray300
                    )

                    Text(
                        text = "이미지 추가",
                        style = TogedyTheme.typography.body12m.copy(
                            color = TogedyTheme.colors.gray500
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            } else {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "선택된 스터디 이미지",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

