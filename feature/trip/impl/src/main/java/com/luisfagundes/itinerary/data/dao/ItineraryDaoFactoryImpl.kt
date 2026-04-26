package com.luisfagundes.itinerary.data.dao

import com.luisfagundes.common.data.database.TripDatabase
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import javax.inject.Inject

internal class ItineraryDaoFactoryImpl @Inject constructor(
    private val database: TripDatabase
) : ItineraryDaoFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <T> getDao(type: ItineraryItemType): BaseItineraryItemDao<T> {
        return when (type) {
            ItineraryItemType.FLIGHT -> database.flightDao()
            ItineraryItemType.ACCOMMODATION -> database.accommodationDao()
            ItineraryItemType.RESTAURANT -> database.restaurantDao()
            ItineraryItemType.ACTIVITY -> database.activityDao()
        } as BaseItineraryItemDao<T>
    }
}