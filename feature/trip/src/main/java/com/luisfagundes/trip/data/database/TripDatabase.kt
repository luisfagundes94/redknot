package com.luisfagundes.trip.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luisfagundes.trip.data.dao.TripDao
import com.luisfagundes.trip.data.database.converters.AirportConverter
import com.luisfagundes.trip.data.database.converters.ItineraryConverter
import com.luisfagundes.trip.data.database.converters.ItineraryDayListConverter
import com.luisfagundes.trip.data.database.converters.ItineraryItemListConverter
import com.luisfagundes.trip.data.database.converters.LocalDateConverter
import com.luisfagundes.trip.data.database.converters.LocalTimeConverter
import com.luisfagundes.trip.data.database.converters.TripStatusConverter
import com.luisfagundes.trip.data.model.TripEntity

@Database(entities = [TripEntity::class], version = 2)
@TypeConverters(
    TripStatusConverter::class,
    LocalDateConverter::class,
    LocalTimeConverter::class,
    AirportConverter::class,
    ItineraryItemListConverter::class,
    ItineraryDayListConverter::class,
    ItineraryConverter::class
)
internal abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
}