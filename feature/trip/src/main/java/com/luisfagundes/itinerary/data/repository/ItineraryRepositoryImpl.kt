package com.luisfagundes.itinerary.data.repository

import com.luisfagundes.itinerary.data.datasource.ItineraryLocalDataSource
import com.luisfagundes.itinerary.data.mapper.ItineraryItemMapper
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.repository.ItineraryRepository
import javax.inject.Inject

internal class ItineraryRepositoryImpl @Inject constructor(
    private val dataSource: ItineraryLocalDataSource,
    private val mapper: ItineraryItemMapper
): ItineraryRepository {
    override suspend fun getItineraryItemList(tripId: Int): Result<List<ItineraryItem>> {
        return dataSource.getItineraryItemList(tripId).map { entities ->
            entities.map { mapper.toDomain(it) }
        }
    }
}