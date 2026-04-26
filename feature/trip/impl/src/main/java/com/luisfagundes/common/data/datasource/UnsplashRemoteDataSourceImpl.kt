package com.luisfagundes.common.data.datasource

import com.luisfagundes.common.data.service.UnsplashApiService
import javax.inject.Inject

internal class UnsplashRemoteDataSourceImpl @Inject constructor(
    private val apiService: UnsplashApiService
): UnsplashRemoteDataSource {
    override suspend fun getImageUrl(location: String): Result<String> {
        return runCatching {
            val response = apiService.getFirstPhoto(query = location)
            response.results.firstOrNull()?.urls?.regular.orEmpty()
        }
    }
}