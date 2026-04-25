package com.luisfagundes.itinerary.domain.repository

import com.luisfagundes.itinerary.domain.model.ItineraryItem

internal interface ItineraryRepository {
    suspend fun getItineraryItemList(tripId: Int): Result<List<ItineraryItem>>
    suspend fun createItineraryItem(item: ItineraryItem): Result<Unit>
}