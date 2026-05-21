package com.luisfagundes.budget.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.luisfagundes.trip.data.model.TripEntity

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["trip_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("trip_id")]
)
internal data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("trip_id") val tripId: Int,
    @ColumnInfo("amount") val amount: String,
    @ColumnInfo("category") val category: String,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("description") val description: String?
)
