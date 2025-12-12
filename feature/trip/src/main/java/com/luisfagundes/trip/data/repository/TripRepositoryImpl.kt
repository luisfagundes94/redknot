package com.luisfagundes.trip.data.repository

import com.luisfagundes.trip.data.datasource.TripLocalDataSource
import com.luisfagundes.trip.data.datasource.TripRemoteDataSource
import com.luisfagundes.trip.data.mapper.TripMapper
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class TripRepositoryImpl @Inject constructor(
    private val localDataSource: TripLocalDataSource,
    private val remoteDataSource: TripRemoteDataSource,
    private val mapper: TripMapper
) : TripRepository {
    override suspend fun getTripList(): Result<List<Trip>> {
        return runCatching {
            localDataSource.getAllTrips().map { mapper.mapToDomain(it) }
        }
    }

    override suspend fun createTrip(trip: Trip): Result<Unit> {
        return runCatching {
            val tripEntity = mapper.mapToEntity(trip)
            localDataSource.createTrip(tripEntity)
        }
    }

    override suspend fun getTripImageUrl(location: String): Result<String> {
        return remoteDataSource.getTripImageUrl(location)
    }
}