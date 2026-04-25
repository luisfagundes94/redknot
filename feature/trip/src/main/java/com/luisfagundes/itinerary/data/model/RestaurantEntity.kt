package com.luisfagundes.itinerary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.trip.data.model.TripEntity
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "restaurants",
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
internal data class RestaurantEntity(
    @PrimaryKey override val id: String,
    @ColumnInfo("trip_id") override val tripId: Int,
    @ColumnInfo("date") override val date: LocalDate,
    @ColumnInfo("time") override val time: LocalTime,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("address") val address: String,
    @ColumnInfo("meal_type") val mealType: String
): ItineraryItemEntity {
    override fun toItineraryItemType() = ItineraryItemType.RESTAURANT
}
