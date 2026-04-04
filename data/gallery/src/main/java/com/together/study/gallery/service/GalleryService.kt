package com.together.study.gallery.service

import com.together.study.remote.model.EmptyDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface GalleryService {
    @Multipart
    @PUT("planners/daily/image")
    suspend fun uploadPlannerImage(
        @Query("date") date: String,
        @Part plannerImage: MultipartBody.Part?,
        @Part("removePlannerImage") removePlannerImage: RequestBody,
    ): EmptyDataResponse
}
