package com.luisfagundes.trip.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luisfagundes.trip.data.dao.TripDao
import com.luisfagundes.trip.data.database.converters.TripStatusConverter
import com.luisfagundes.trip.data.model.TripEntity

@Database(entities = [TripEntity::class], version = 1)
@TypeConverters(TripStatusConverter::class)
internal abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
}