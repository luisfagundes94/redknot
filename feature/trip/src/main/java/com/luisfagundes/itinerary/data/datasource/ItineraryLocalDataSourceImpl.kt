package com.luisfagundes.itinerary.data.datasource

import com.luisfagundes.common.data.database.TripDatabase
import com.luisfagundes.itinerary.data.model.AccommodationEntity
import com.luisfagundes.itinerary.data.model.ActivityEntity
import com.luisfagundes.itinerary.data.model.FlightEntity
import com.luisfagundes.itinerary.data.model.RestaurantEntity
import javax.inject.Inject

internal class ItineraryLocalDataSourceImpl @Inject constructor(
    database: TripDatabase
) : ItineraryLocalDataSource {
    private val flightDao = database.flightDao()
    private val accommodationDao = database.accommodationDao()
    private val restaurantDao = database.restaurantDao()
    private val activityDao = database.activityDao()

    override suspend fun getFlights(tripId: Int): Result<List<FlightEntity>> {
        return runCatching { flightDao.getByTripId(tripId) }
    }

    override suspend fun getAccommodations(tripId: Int): Result<List<AccommodationEntity>> {
        return runCatching { accommodationDao.getByTripId(tripId) }
    }

    override suspend fun getRestaurants(tripId: Int): Result<List<RestaurantEntity>> {
        return runCatching { restaurantDao.getByTripId(tripId) }
    }

    override suspend fun getActivities(tripId: Int): Result<List<ActivityEntity>> {
        return runCatching { activityDao.getByTripId(tripId) }
    }
}
