package com.luisfagundes.trip.data.datasource

import com.luisfagundes.trip.data.database.TripDatabase
import com.luisfagundes.trip.data.model.ItineraryItemEntity
import javax.inject.Inject

internal class TripItineraryLocalDataSourceImpl @Inject constructor(
    private val database: TripDatabase
) : TripItineraryLocalDataSource {
    override suspend fun getItineraryItems(tripId: Int): Result<List<ItineraryItemEntity>> {
        return runCatching { database.itineraryItemDao().getItemsForTrip(tripId) }
    }

    override suspend fun getItineraryItemById(itemId: String): Result<ItineraryItemEntity> {
        return runCatching { database.itineraryItemDao().getItemById(itemId) }
    }

    override suspend fun addItineraryItem(item: ItineraryItemEntity): Result<Unit> {
        return runCatching { database.itineraryItemDao().insertItem(item) }
    }

    override suspend fun updateItineraryItem(item: ItineraryItemEntity): Result<Unit> {
        return runCatching { database.itineraryItemDao().updateItem(item) }
    }

    override suspend fun deleteItineraryItem(item: ItineraryItemEntity): Result<Unit> {
        return runCatching { database.itineraryItemDao().deleteItem(item) }
    }
}