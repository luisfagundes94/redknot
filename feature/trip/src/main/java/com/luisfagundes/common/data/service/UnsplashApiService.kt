package com.luisfagundes.common.data.service

import com.luisfagundes.trip.data.model.PhotoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val FIRST_PAGE = 1
private const val SINGLE_ITEM = 1
private const val PHOTO_ORIENTATION = "landscape"

internal interface UnsplashApiService {
    @GET("search/photos")
    suspend fun getFirstPhoto(
        @Query("query") query: String,
        @Query("page") page: Int = FIRST_PAGE,
        @Query("per_page") perPage: Int = SINGLE_ITEM,
        @Query("orientation") orientation: String = PHOTO_ORIENTATION
    ): PhotoSearchResponse
}