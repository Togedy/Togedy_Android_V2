package com.together.study.calendar.service

import com.together.study.calendar.dto.CategoryRequest
import com.together.study.calendar.dto.CategoryResponse
import com.together.study.remote.model.BaseResponse
import com.together.study.remote.model.EmptyDataResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryService {
    @POST("calendars/categories")
    suspend fun postCategory(
        @Body category: CategoryRequest,
    ): EmptyDataResponse

    @GET("calendars/categories")
    suspend fun getCategoryItems(): BaseResponse<List<CategoryResponse>>

    @PATCH("calendars/categories/{categoryId}")
    suspend fun patchCategory(
        @Path("categoryId") categoryId: Long,
        @Body category: CategoryRequest,
    ): EmptyDataResponse

    @DELETE("calendars/categories/{categoryId}")
    suspend fun deleteCategory(
        @Path("categoryId") categoryId: Long,
    ): EmptyDataResponse
}
