package com.luisfagundes.itinerary.data.datasource

import com.luisfagundes.itinerary.data.model.ItineraryItemEntity

internal interface ItineraryLocalDataSource {
    suspend fun getItineraryItemList(tripId: Int): Result<List<ItineraryItemEntity>>
}
