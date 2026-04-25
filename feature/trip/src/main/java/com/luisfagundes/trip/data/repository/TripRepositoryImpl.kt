package com.luisfagundes.trip.data.repository

import com.luisfagundes.common.data.datasource.UnsplashRemoteDataSource
import com.luisfagundes.trip.data.datasource.TripLocalDataSource
import com.luisfagundes.trip.data.mapper.TripMapper
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class TripRepositoryImpl @Inject constructor(
    private val tripDataSource: TripLocalDataSource,
    private val unsplashDataSource: UnsplashRemoteDataSource,
    private val mapper: TripMapper
) : TripRepository {
    override suspend fun getTripList(): Result<List<Trip>> {
        return tripDataSource.getAllTrips().map { tripEntities ->
            tripEntities.map { mapper.mapToDomain(it) }
        }
    }

    override suspend fun getTripById(id: Int): Result<Trip> {
        return tripDataSource.getTripById(id).map { tripEntity ->
            mapper.mapToDomain(tripEntity)
        }
    }

    override suspend fun createTrip(trip: Trip): Result<Unit> {
        return tripDataSource.createTrip(tripEntity = mapper.mapToEntity(trip))
    }

    override suspend fun deleteTripById(id: Int): Result<Unit> {
        return tripDataSource.deleteTripById(id)
    }

    override suspend fun getTripImageUrl(location: String): Result<String> {
        return unsplashDataSource.getImageUrl(location)
    }
}