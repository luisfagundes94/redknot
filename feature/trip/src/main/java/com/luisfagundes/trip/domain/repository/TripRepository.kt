package com.luisfagundes.trip.domain.repository

import com.luisfagundes.trip.domain.model.Trip

internal interface TripRepository {
    suspend fun getTripList(): Result<List<Trip>>
    suspend fun getTripById(id: Int): Result<Trip>
    suspend fun createTrip(trip: Trip): Result<Unit>
    suspend fun getTripImageUrl(location: String): Result<String>
}