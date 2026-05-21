package com.luisfagundes.budget.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.luisfagundes.budget.data.model.ExpenseEntity

@Dao
internal interface ExpenseDao {
    @Insert
    suspend fun insert(entity: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE trip_id = :tripId")
    suspend fun getByTripId(tripId: Int): List<ExpenseEntity>

    @Delete
    suspend fun delete(entity: ExpenseEntity)
}
