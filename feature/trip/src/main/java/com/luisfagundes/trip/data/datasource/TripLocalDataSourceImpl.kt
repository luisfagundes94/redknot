package com.luisfagundes.trip.data.datasource

import com.luisfagundes.trip.data.dao.TripDao
import com.luisfagundes.trip.data.model.TripEntity
import javax.inject.Inject

internal class TripLocalDataSourceImpl @Inject constructor(
    private val tripDao: TripDao,
) : TripLocalDataSource {
    override suspend fun getAllTrips(): Result<List<TripEntity>> {
        return runCatching { tripDao.getAllTrips() }
    }

    override suspend fun getTripById(id: Int): Result<TripEntity> {
        return runCatching { tripDao.getTripById(id) }
    }

    override suspend fun createTrip(tripEntity: TripEntity): Result<Unit> {
        return runCatching { tripDao.createTrip(tripEntity) }
    }

    override suspend fun deleteTrip(tripEntity: TripEntity): Result<Unit> {
        return runCatching { tripDao.deleteTrip(tripEntity) }
    }
}