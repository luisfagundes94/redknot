package com.luisfagundes.itinerary.data.datasource

import com.luisfagundes.itinerary.data.model.ItineraryItemEntity
import com.luisfagundes.itinerary.domain.model.ItineraryItemType

internal interface ItineraryLocalDataSource {
    suspend fun getItineraryItemList(tripId: Int): Result<List<ItineraryItemEntity>>
    suspend fun getItineraryItemById(id: String, type: ItineraryItemType): Result<ItineraryItemEntity?>
    suspend fun createItineraryItem(entity: Any, type: ItineraryItemType): Result<Unit>
    suspend fun updateItineraryItem(entity: Any, type: ItineraryItemType): Result<Unit>
    suspend fun deleteItineraryItem(id: String, type: ItineraryItemType): Result<Unit>
}
