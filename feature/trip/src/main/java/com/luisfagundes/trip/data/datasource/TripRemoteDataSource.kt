package com.luisfagundes.trip.data.datasource

internal interface TripRemoteDataSource {
    suspend fun getTripImageUrl(location: String): Result<String>
}