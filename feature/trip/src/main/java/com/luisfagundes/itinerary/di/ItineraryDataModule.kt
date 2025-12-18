package com.luisfagundes.itinerary.di

import com.luisfagundes.itinerary.data.datasource.ItineraryLocalDataSource
import com.luisfagundes.itinerary.data.datasource.ItineraryLocalDataSourceImpl
import com.luisfagundes.itinerary.data.mapper.AirportMapper
import com.luisfagundes.itinerary.data.mapper.ItineraryItemMapper
import com.luisfagundes.itinerary.data.repository.ItineraryRepositoryImpl
import com.luisfagundes.itinerary.domain.repository.ItineraryRepository
import com.luisfagundes.common.data.database.TripDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ItineraryDataModule {
    @Provides
    @Singleton
    fun provideItineraryDataSource(
        database: TripDatabase
    ): ItineraryLocalDataSource {
        return ItineraryLocalDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun provideItineraryItemMapper(airportMapper: AirportMapper): ItineraryItemMapper {
        return ItineraryItemMapper(airportMapper)
    }

    @Provides
    @Singleton
    fun provideAirportMapper(): AirportMapper {
        return AirportMapper()
    }

    @Provides
    @Singleton
    fun provideItineraryRepository(
        localDataSource: ItineraryLocalDataSource,
        mapper: ItineraryItemMapper
    ): ItineraryRepository {
        return ItineraryRepositoryImpl(
            dataSource = localDataSource,
            mapper = mapper
        )
    }
}