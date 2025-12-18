package com.luisfagundes.itinerary.domain.repository

import com.luisfagundes.itinerary.domain.model.ItineraryItem

internal interface ItineraryRepository {
    suspend fun getItineraryItems(tripId: Int): Result<List<ItineraryItem>>
    suspend fun addItineraryItem(item: ItineraryItem): Result<Unit>
    suspend fun updateItineraryItem(item: ItineraryItem): Result<Unit>
    suspend fun deleteItineraryItem(item: ItineraryItem): Result<Unit>
}