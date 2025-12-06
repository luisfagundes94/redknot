package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.datasource.TripDataSource
import com.luisfagundes.trip.data.datasource.TripDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TripDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindTripDataSource(
        impl: TripDataSourceImpl
    ): TripDataSource
}