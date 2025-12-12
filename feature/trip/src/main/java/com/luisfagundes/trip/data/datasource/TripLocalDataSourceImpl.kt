package com.luisfagundes.trip.data.datasource

import com.luisfagundes.trip.data.database.TripDatabase
import com.luisfagundes.trip.data.model.TripEntity
import javax.inject.Inject

internal class TripLocalDataSourceImpl @Inject constructor(
    private val database: TripDatabase
) : TripLocalDataSource {
    override suspend fun getAllTrips(): List<TripEntity> {
        return database.tripDao().getAllTrips()
    }

    override suspend fun getTripById(id: Int): TripEntity? {
        return database.tripDao().getTripById(id)
    }

    override suspend fun createTrip(tripEntity: TripEntity) {
        database.tripDao().createTrip(tripEntity)
    }

    override suspend fun deleteTrip(tripEntity: TripEntity) {
        database.tripDao().deleteTrip(tripEntity)
    }
}