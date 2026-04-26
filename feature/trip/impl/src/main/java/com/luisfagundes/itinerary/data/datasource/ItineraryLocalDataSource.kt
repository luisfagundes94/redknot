package com.luisfagundes.itinerary.data.datasource

import com.luisfagundes.itinerary.data.model.ItineraryItemEntity
import com.luisfagundes.itinerary.domain.model.ItineraryItemType

internal interface ItineraryLocalDataSource {
    suspend fun getItineraryItemList(tripId: Int): Result<List<ItineraryItemEntity>>
    suspend fun createItineraryItem(entity: Any, type: ItineraryItemType): Result<Unit>
}
