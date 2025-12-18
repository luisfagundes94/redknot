package com.luisfagundes.itinerary.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.luisfagundes.itinerary.data.model.RestaurantEntity

@Dao
internal interface RestaurantDao : BaseItineraryItemDao<RestaurantEntity> {
    @Query("SELECT * FROM restaurants WHERE trip_id = :tripId ORDER BY date, time")
    override suspend fun getByTripId(tripId: Int): List<RestaurantEntity>
}