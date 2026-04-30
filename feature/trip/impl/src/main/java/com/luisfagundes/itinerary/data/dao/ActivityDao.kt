package com.luisfagundes.itinerary.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.luisfagundes.itinerary.data.model.ActivityEntity

@Dao
internal interface ActivityDao : BaseItineraryItemDao<ActivityEntity> {
    @Query("SELECT * FROM activities WHERE trip_id = :tripId ORDER BY date, time")
    override suspend fun getByTripId(tripId: Int): List<ActivityEntity>

    @Query("SELECT * FROM activities WHERE id = :id")
    override suspend fun getById(id: String): ActivityEntity?
}