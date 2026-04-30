package com.luisfagundes.itinerary.data.repository

import com.luisfagundes.itinerary.data.datasource.ItineraryLocalDataSource
import com.luisfagundes.itinerary.data.mapper.ItineraryItemMapper
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
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

    override suspend fun getItineraryItemById(
        id: String,
        type: ItineraryItemType
    ): Result<ItineraryItem?> {
        return dataSource.getItineraryItemById(id, type).map { entity ->
            entity?.let { mapper.toDomain(it) }
        }
    }

    override suspend fun createItineraryItem(item: ItineraryItem): Result<Unit> {
        val entity = mapper.toEntity(item)
        val type = mapper.toType(item)
        return dataSource.createItineraryItem(entity, type)
    }

    override suspend fun updateItineraryItem(item: ItineraryItem): Result<Unit> {
        val entity = mapper.toEntity(item)
        val type = mapper.toType(item)
        return dataSource.updateItineraryItem(entity, type)
    }

    override suspend fun deleteItineraryItem(id: String, type: ItineraryItemType): Result<Unit> {
        return dataSource.deleteItineraryItem(id, type)
    }
}