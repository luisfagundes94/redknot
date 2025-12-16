package com.luisfagundes.trip.data.repository

import com.luisfagundes.trip.data.datasource.TripItineraryLocalDataSource
import com.luisfagundes.trip.data.mapper.TripItineraryItemMapper
import com.luisfagundes.trip.domain.model.ItineraryItem
import com.luisfagundes.trip.domain.repository.TripItineraryRepository
import javax.inject.Inject

internal class TripItineraryRepositoryImpl @Inject constructor(
    private val localDataSource: TripItineraryLocalDataSource,
    private val mapper: TripItineraryItemMapper
): TripItineraryRepository {
    override suspend fun getItineraryItems(tripId: Int): Result<List<ItineraryItem>> {
        return localDataSource.getItineraryItems(tripId).map { itineraryItemEntities ->
            itineraryItemEntities.map { itineraryItemEntity ->
                mapper.mapToDomain(source = itineraryItemEntity)
            }
        }
    }

    override suspend fun getItineraryItemById(itemId: String): Result<ItineraryItem> {
        return localDataSource.getItineraryItemById(itemId).map { itineraryItemEntity ->
            mapper.mapToDomain(itineraryItemEntity)
        }
    }

    override suspend fun addItineraryItem(item: ItineraryItem): Result<Unit> {
        return localDataSource.addItineraryItem(item = mapper.mapToEntity(item))
    }

    override suspend fun updateItineraryItem(item: ItineraryItem): Result<Unit> {
        return localDataSource.updateItineraryItem(item = mapper.mapToEntity(item))
    }

    override suspend fun deleteItineraryItem(item: ItineraryItem): Result<Unit> {
        return localDataSource.deleteItineraryItem(item = mapper.mapToEntity(item))
    }

}