package com.luisfagundes.itinerary.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

internal interface BaseItineraryItemDao<T> {
    suspend fun getByTripId(tripId: Int): List<T>
    suspend fun getById(id: String): T?

    @Insert
    suspend fun insert(item: T)

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)
}