package com.luisfagundes.trip.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luisfagundes.trip.data.dao.ItineraryItemDao
import com.luisfagundes.trip.data.dao.TripDao
import com.luisfagundes.trip.data.database.converters.AirportConverter
import com.luisfagundes.trip.data.database.converters.LocalDateConverter
import com.luisfagundes.trip.data.database.converters.LocalTimeConverter
import com.luisfagundes.trip.data.database.converters.TripStatusConverter
import com.luisfagundes.trip.data.model.ItineraryItemEntity
import com.luisfagundes.trip.data.model.TripEntity

@Database(
    entities = [TripEntity::class, ItineraryItemEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(
    TripStatusConverter::class,
    LocalDateConverter::class,
    LocalTimeConverter::class,
    AirportConverter::class
)
internal abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun itineraryItemDao(): ItineraryItemDao
}