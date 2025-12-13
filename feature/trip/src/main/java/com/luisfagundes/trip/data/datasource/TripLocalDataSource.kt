package com.luisfagundes.trip.data.datasource

import com.luisfagundes.trip.data.model.TripEntity

internal interface TripLocalDataSource {
    suspend fun getAllTrips(): Result<List<TripEntity>>
    suspend fun getTripById(id: Int): Result<TripEntity>
    suspend fun createTrip(tripEntity: TripEntity): Result<Unit>
    suspend fun deleteTrip(tripEntity: TripEntity)
}