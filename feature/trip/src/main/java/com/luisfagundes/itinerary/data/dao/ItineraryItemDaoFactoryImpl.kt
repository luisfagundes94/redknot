package com.luisfagundes.itinerary.data.dao

import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import javax.inject.Inject

internal class ItineraryItemDaoFactoryImpl @Inject constructor(
    private val flightDao: FlightDao,
    private val accommodationDao: AccommodationDao,
    private val restaurantDao: RestaurantDao,
    private val activityDao: ActivityDao
) : ItineraryItemDaoFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <T> getDao(type: ItineraryItemType): BaseItineraryItemDao<T> {
        return when (type) {
            ItineraryItemType.FLIGHT -> flightDao
            ItineraryItemType.ACCOMMODATION -> accommodationDao
            ItineraryItemType.RESTAURANT -> restaurantDao
            ItineraryItemType.ACTIVITY -> activityDao
        } as BaseItineraryItemDao<T>
    }
}