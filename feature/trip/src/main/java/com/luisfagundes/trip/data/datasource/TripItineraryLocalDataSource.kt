package com.luisfagundes.trip.data.datasource

import com.luisfagundes.trip.data.model.ItineraryItemEntity

internal interface TripItineraryLocalDataSource {
    suspend fun getItineraryItems(tripId: Int): Result<List<ItineraryItemEntity>>
    suspend fun getItineraryItemById(itemId: String): Result<ItineraryItemEntity>
    suspend fun addItineraryItem(item: ItineraryItemEntity): Result<Unit>
    suspend fun updateItineraryItem(item: ItineraryItemEntity): Result<Unit>
    suspend fun deleteItineraryItem(item: ItineraryItemEntity): Result<Unit>
}