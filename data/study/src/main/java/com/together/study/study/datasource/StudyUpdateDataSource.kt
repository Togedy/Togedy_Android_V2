package com.together.study.study.datasource

import android.content.Context
import android.net.Uri
import com.together.study.study.service.StudyUpdateService
import com.together.study.util.ImageConverter
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class StudyUpdateDataSource @Inject constructor(
    private val studyUpdateService: StudyUpdateService,
    @ApplicationContext private val context: Context,
) {
    suspend fun createStudy(
        challengeGoalTime: Int?,
        studyName: String,
        studyDescription: String?,
        studyMemberLimit: Int,
        studyTag: String,
        studyPassword: String?,
        studyImageUri: Uri?,
    ) = studyUpdateService.createStudy(
        challengeGoalTime = challengeGoalTime?.toString()
            ?.toRequestBody("text/plain".toMediaType()),
        studyName = studyName.toRequestBody("text/plain".toMediaType()),
        studyDescription = studyDescription?.toRequestBody("text/plain".toMediaType()),
        studyMemberLimit = studyMemberLimit.toString().toRequestBody("text/plain".toMediaType()),
        studyTag = studyTag.toRequestBody("text/plain".toMediaType()),
        studyPassword = studyPassword?.toRequestBody("text/plain".toMediaType()),
        studyImage = studyImageUri?.let { uri ->
            val file = ImageConverter.uriToFile(context, uri)
            val requestFile = file.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("studyImage", file.name, requestFile)
        },
    )

    suspend fun checkStudyNameDuplicate(name: String) =
        studyUpdateService.checkStudyNameDuplicate(name)

    suspend fun updateStudy(
        studyId: Long,
        challengeGoalTime: Int?,
        studyName: String,
        studyDescription: String?,
        studyMemberLimit: Int,
        studyTag: String,
        studyPassword: String?,
        studyImageUri: Uri?,
    ) = studyUpdateService.updateStudy(
        studyId = studyId,
        challengeGoalTime = challengeGoalTime?.toString()
            ?.toRequestBody("text/plain".toMediaType()),
        studyName = studyName.toRequestBody("text/plain".toMediaType()),
        studyDescription = studyDescription?.toRequestBody("text/plain".toMediaType()),
        studyMemberLimit = studyMemberLimit.toString().toRequestBody("text/plain".toMediaType()),
        studyTag = studyTag.toRequestBody("text/plain".toMediaType()),
        studyPassword = studyPassword?.toRequestBody("text/plain".toMediaType()),
        studyImage = studyImageUri?.takeIf { uri ->
            // HTTP/HTTPS URL인 경우 이미 서버에 업로드된 이미지이므로 업로드하지 않음
            uri.scheme != "http" && uri.scheme != "https"
        }?.let { uri ->
            val file = ImageConverter.uriToFile(context, uri)
            val requestFile = file.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("studyImage", file.name, requestFile)
        },
    )
}

