package com.luisfagundes.itinerary.data.datasource

import com.luisfagundes.itinerary.data.dao.ItineraryDaoFactory
import com.luisfagundes.itinerary.data.model.ItineraryItemEntity
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import javax.inject.Inject

internal class ItineraryLocalDataSourceImpl @Inject constructor(
    private val itineraryDaoFactory: ItineraryDaoFactory
) : ItineraryLocalDataSource {
    override suspend fun getItineraryItemList(tripId: Int): Result<List<ItineraryItemEntity>> {
        return runCatching {
            ItineraryItemType.entries.flatMap { type ->
                itineraryDaoFactory.getDao<ItineraryItemEntity>(type).getByTripId(tripId)
            }.sortedWith(compareBy({ it.date }, { it.time }))
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun createItineraryItem(entity: Any, type: ItineraryItemType): Result<Unit> {
        return runCatching {
            itineraryDaoFactory.getDao<Any>(type).insert(entity)
        }
    }
}
