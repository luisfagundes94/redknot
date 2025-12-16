package com.luisfagundes.trip.di

import android.content.Context
import androidx.room.Room
import com.luisfagundes.trip.data.database.TripDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TRIP_DATABASE_NAME = "trip_database"

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideTripDatabase(
        @ApplicationContext applicationContext: Context
    ) = Room.databaseBuilder(
        applicationContext,
        TripDatabase::class.java, TRIP_DATABASE_NAME
    )
        .fallbackToDestructiveMigration(false)
        .build()
}