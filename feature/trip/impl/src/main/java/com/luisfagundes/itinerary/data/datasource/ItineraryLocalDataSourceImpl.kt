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

    override suspend fun getItineraryItemById(
        id: String,
        type: ItineraryItemType
    ): Result<ItineraryItemEntity?> {
        return runCatching {
            itineraryDaoFactory.getDao<ItineraryItemEntity>(type).getById(id)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun createItineraryItem(entity: Any, type: ItineraryItemType): Result<Unit> {
        return runCatching {
            itineraryDaoFactory.getDao<Any>(type).insert(entity)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun updateItineraryItem(entity: Any, type: ItineraryItemType): Result<Unit> {
        return runCatching {
            itineraryDaoFactory.getDao<Any>(type).update(entity)
        }
    }

    override suspend fun deleteItineraryItem(id: String, type: ItineraryItemType): Result<Unit> {
        return runCatching {
            val dao = itineraryDaoFactory.getDao<ItineraryItemEntity>(type)
            val entity = dao.getById(id) ?: return@runCatching
            dao.delete(entity)
        }
    }
}
