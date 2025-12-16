package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.mapper.AirportMapper
import com.luisfagundes.trip.data.mapper.ItineraryDayMapper
import com.luisfagundes.trip.data.mapper.ItineraryItemMapper
import com.luisfagundes.trip.data.mapper.ItineraryMapper
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
    fun provideTripMapper(itineraryMapper: ItineraryMapper) =
        TripMapper(itineraryMapper)

    @Provides
    @Singleton
    fun provideItineraryMapper(itineraryDayMapper: ItineraryDayMapper) =
        ItineraryMapper(itineraryDayMapper)

    @Provides
    @Singleton
    fun provideItineraryDayMapper(itineraryItemMapper: ItineraryItemMapper) =
        ItineraryDayMapper(itineraryItemMapper)

    @Provides
    @Singleton
    fun provideItineraryItemMapper(airportMapper: AirportMapper) =
        ItineraryItemMapper(airportMapper)

    @Provides
    @Singleton
    fun provideAirportMapper() =
        AirportMapper()
}