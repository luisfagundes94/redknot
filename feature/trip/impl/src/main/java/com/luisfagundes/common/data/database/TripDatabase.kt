package com.luisfagundes.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luisfagundes.budget.data.dao.ExpenseDao
import com.luisfagundes.budget.data.model.ExpenseEntity
import com.luisfagundes.documents.data.dao.DocumentDao
import com.luisfagundes.documents.data.model.DocumentEntity
import com.luisfagundes.itinerary.data.dao.AccommodationDao
import com.luisfagundes.itinerary.data.dao.ActivityDao
import com.luisfagundes.itinerary.data.dao.FlightDao
import com.luisfagundes.itinerary.data.dao.RestaurantDao
import com.luisfagundes.trip.data.dao.TripDao
import com.luisfagundes.common.data.database.converters.DurationConverter
import com.luisfagundes.common.data.database.converters.LocalDateConverter
import com.luisfagundes.common.data.database.converters.LocalTimeConverter
import com.luisfagundes.common.data.database.converters.TripStatusConverter
import com.luisfagundes.itinerary.data.model.AccommodationEntity
import com.luisfagundes.itinerary.data.model.ActivityEntity
import com.luisfagundes.itinerary.data.model.FlightEntity
import com.luisfagundes.itinerary.data.model.RestaurantEntity
import com.luisfagundes.trip.data.model.TripEntity

@Database(
    entities = [
        TripEntity::class,
        FlightEntity::class,
        AccommodationEntity::class,
        RestaurantEntity::class,
        ActivityEntity::class,
        DocumentEntity::class,
        ExpenseEntity::class
    ],
    version = 8,
    exportSchema = false
)
@TypeConverters(
    TripStatusConverter::class,
    LocalDateConverter::class,
    LocalTimeConverter::class,
    DurationConverter::class
)
internal abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun flightDao(): FlightDao
    abstract fun accommodationDao(): AccommodationDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun activityDao(): ActivityDao
    abstract fun documentDao(): DocumentDao
    abstract fun expenseDao(): ExpenseDao
}
