package com.luisfagundes.itinerary.domain.repository

import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.model.ItineraryItemType

internal interface ItineraryRepository {
    suspend fun getItineraryItemList(tripId: Int): Result<List<ItineraryItem>>
    suspend fun getItineraryItemById(id: String, type: ItineraryItemType): Result<ItineraryItem?>
    suspend fun createItineraryItem(item: ItineraryItem): Result<Unit>
    suspend fun updateItineraryItem(item: ItineraryItem): Result<Unit>
    suspend fun deleteItineraryItem(id: String, type: ItineraryItemType): Result<Unit>
}