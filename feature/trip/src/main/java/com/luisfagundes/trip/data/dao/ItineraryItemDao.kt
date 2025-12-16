package com.luisfagundes.trip.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.luisfagundes.trip.data.model.ItineraryItemEntity

@Dao
internal interface ItineraryItemDao {
    @Query("SELECT * FROM itinerary_items WHERE trip_id = :tripId ORDER BY date, time")
    suspend fun getItemsForTrip(tripId: Int): List<ItineraryItemEntity>

    @Query("SELECT * FROM itinerary_items WHERE id = :itemId")
    suspend fun getItemById(itemId: String): ItineraryItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItineraryItemEntity)

    @Update
    suspend fun updateItem(item: ItineraryItemEntity)

    @Delete
    suspend fun deleteItem(item: ItineraryItemEntity)

    @Query("DELETE FROM itinerary_items WHERE trip_id = :tripId")
    suspend fun deleteAllItemsForTrip(tripId: Int)
}
