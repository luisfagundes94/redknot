package com.luisfagundes.itinerary.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.luisfagundes.itinerary.data.model.FlightEntity

@Dao
internal interface FlightDao : BaseItineraryItemDao<FlightEntity> {
    @Query("SELECT * FROM flights WHERE trip_id = :tripId ORDER BY date, time")
    override suspend fun getByTripId(tripId: Int): List<FlightEntity>

    @Query("SELECT * FROM flights WHERE id = :id")
    override suspend fun getById(id: String): FlightEntity?
}