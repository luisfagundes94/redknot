package com.luisfagundes.itinerary.data.datasource

import com.luisfagundes.itinerary.data.model.AccommodationEntity
import com.luisfagundes.itinerary.data.model.ActivityEntity
import com.luisfagundes.itinerary.data.model.FlightEntity
import com.luisfagundes.itinerary.data.model.RestaurantEntity

internal interface ItineraryLocalDataSource {
    suspend fun getFlights(tripId: Int): Result<List<FlightEntity>>
    suspend fun getAccommodations(tripId: Int): Result<List<AccommodationEntity>>
    suspend fun getRestaurants(tripId: Int): Result<List<RestaurantEntity>>
    suspend fun getActivities(tripId: Int): Result<List<ActivityEntity>>
}
