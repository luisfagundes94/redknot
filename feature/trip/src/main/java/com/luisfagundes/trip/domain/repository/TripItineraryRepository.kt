package com.luisfagundes.trip.domain.repository

import com.luisfagundes.trip.domain.model.ItineraryItem

internal interface TripItineraryRepository {
    suspend fun getItineraryItems(tripId: Int): Result<List<ItineraryItem>>
    suspend fun getItineraryItemById(itemId: String): Result<ItineraryItem>
    suspend fun addItineraryItem(item: ItineraryItem): Result<Unit>
    suspend fun updateItineraryItem(item: ItineraryItem): Result<Unit>
    suspend fun deleteItineraryItem(item: ItineraryItem): Result<Unit>
}