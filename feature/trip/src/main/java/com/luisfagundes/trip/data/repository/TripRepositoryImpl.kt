package com.luisfagundes.trip.data.repository

import com.luisfagundes.trip.data.datasource.TripDataSource
import com.luisfagundes.trip.data.mapper.TripMapper
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class TripRepositoryImpl @Inject constructor(
    private val datasource: TripDataSource,
    private val mapper: TripMapper
) : TripRepository {
    override suspend fun getTripList(): Result<List<Trip>> {
        return runCatching {
            datasource.getAllTrips().map { mapper.mapToDomain(it) }
        }
    }

    override suspend fun createTrip(trip: Trip): Result<Unit> {
        return runCatching {
            val tripEntity = mapper.mapToEntity(trip)
            datasource.createTrip(tripEntity)
        }
    }
}