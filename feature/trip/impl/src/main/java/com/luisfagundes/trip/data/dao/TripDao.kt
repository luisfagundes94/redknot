package com.luisfagundes.trip.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luisfagundes.trip.data.model.TripEntity

@Dao
internal interface TripDao {
    @Query("SELECT * FROM trips")
    suspend fun getAllTrips(): List<TripEntity>

    @Query("SELECT * FROM trips WHERE id = :id LIMIT 1")
    suspend fun getTripById(id: Int): TripEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTrip(tripEntity: TripEntity)

    @Query("DELETE FROM trips WHERE id = :id")
    suspend fun deleteTripById(id: Int)

    @Query("UPDATE trips SET total_budget = :totalBudget WHERE id = :tripId")
    suspend fun updateTotalBudget(tripId: Int, totalBudget: String)

    @Query("UPDATE trips SET currency = :currency WHERE id = :tripId")
    suspend fun updateCurrency(tripId: Int, currency: String)
}