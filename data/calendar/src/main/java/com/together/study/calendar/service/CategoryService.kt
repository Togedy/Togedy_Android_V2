package com.together.study.calendar.service

import com.together.study.calendar.dto.CategoryRequest
import com.together.study.calendar.dto.CategoryResponse
import com.together.study.remote.model.BaseResponse
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
    ): BaseResponse<Boolean>

    @GET("calendars/categories")
    suspend fun getCategoryItems(): BaseResponse<List<CategoryResponse>>

    @PATCH("calendars/categories/{categoryId}")
    suspend fun patchCategory(
        @Path("categoryId") categoryId: Long,
        @Body category: CategoryRequest,
    ): BaseResponse<Boolean>

    @DELETE("calendars/categories/{categoryId}")
    suspend fun deleteCategory(
        @Path("categoryId") categoryId: Long,
    ): BaseResponse<Boolean>
}
