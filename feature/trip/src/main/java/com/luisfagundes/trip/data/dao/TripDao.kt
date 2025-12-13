package com.luisfagundes.trip.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.luisfagundes.trip.data.model.TripEntity

@Dao
internal interface TripDao {
    @Query("SELECT * FROM tripentity")
    suspend fun getAllTrips(): List<TripEntity>

    @Query("SELECT * FROM tripentity WHERE id = :id LIMIT 1")
    suspend fun getTripById(id: Int): TripEntity

    @Insert
    suspend fun createTrip(tripEntity: TripEntity)

    @Delete
    suspend fun deleteTrip(tripEntity: TripEntity)
}