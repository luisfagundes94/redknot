package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.mapper.TripMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TripMapperModule {
    @Provides
    @Singleton
    fun provideTripMapper() = TripMapper()
}