package com.luisfagundes.common.data.datasource

internal interface UnsplashRemoteDataSource {
    suspend fun getImageUrl(location: String): Result<String>
}