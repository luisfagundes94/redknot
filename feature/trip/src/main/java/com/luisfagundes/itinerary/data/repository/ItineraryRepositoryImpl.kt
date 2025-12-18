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
    override suspend fun getItineraryItems(tripId: Int): Result<List<ItineraryItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun addItineraryItem(item: ItineraryItem): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateItineraryItem(item: ItineraryItem): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItineraryItem(item: ItineraryItem): Result<Unit> {
        TODO("Not yet implemented")
    }

}