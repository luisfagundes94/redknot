package com.luisfagundes.trip.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luisfagundes.trip.data.dao.TripDao
import com.luisfagundes.trip.data.model.TripEntity

@Database(entities = [TripEntity::class], version = 1)
internal abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
}