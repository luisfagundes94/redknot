package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.repository.TripRepositoryImpl
import com.luisfagundes.trip.domain.repository.TripRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TripRepositoryModule {
    @Binds
    abstract fun bindTripRepository(impl: TripRepositoryImpl): TripRepository
}