package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.datasource.TripLocalDataSource
import com.luisfagundes.trip.data.datasource.TripLocalDataSourceImpl
import com.luisfagundes.trip.data.datasource.TripRemoteDataSource
import com.luisfagundes.trip.data.datasource.TripRemoteDataSourceImpl
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
    abstract fun bindTripLocalDataSource(
        impl: TripLocalDataSourceImpl
    ): TripLocalDataSource

    @Binds
    @Singleton
    abstract fun bindTripRemoteDataSource(
        impl: TripRemoteDataSourceImpl
    ): TripRemoteDataSource
}