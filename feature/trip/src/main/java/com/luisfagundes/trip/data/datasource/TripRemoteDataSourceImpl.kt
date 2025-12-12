package com.luisfagundes.trip.data.datasource

import com.luisfagundes.trip.data.service.UnsplashApiService
import javax.inject.Inject

internal class TripRemoteDataSourceImpl @Inject constructor(
    private val apiService: UnsplashApiService
): TripRemoteDataSource {
    override suspend fun getTripImageUrl(location: String): Result<String> {
        return runCatching {
            val response = apiService.getFirstPhoto(query = location)
            response.results.firstOrNull()?.urls?.regular.orEmpty()
        }
    }
}