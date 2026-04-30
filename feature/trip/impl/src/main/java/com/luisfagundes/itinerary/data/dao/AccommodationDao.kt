package com.luisfagundes.itinerary.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.luisfagundes.itinerary.data.model.AccommodationEntity

@Dao
internal interface AccommodationDao : BaseItineraryItemDao<AccommodationEntity> {
    @Query("SELECT * FROM accommodations WHERE trip_id = :tripId ORDER BY date, time")
    override suspend fun getByTripId(tripId: Int): List<AccommodationEntity>

    @Query("SELECT * FROM accommodations WHERE id = :id")
    override suspend fun getById(id: String): AccommodationEntity?
}