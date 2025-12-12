package com.luisfagundes.trip.data.datasource

import com.luisfagundes.trip.data.model.TripEntity

internal interface TripLocalDataSource {
    suspend fun getAllTrips(): List<TripEntity>
    suspend fun getTripById(id: Int): TripEntity?
    suspend fun createTrip(tripEntity: TripEntity)
    suspend fun deleteTrip(tripEntity: TripEntity)
}