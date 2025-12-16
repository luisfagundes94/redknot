package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.database.TripDatabase
import com.luisfagundes.trip.data.datasource.ItineraryLocalDataSource
import com.luisfagundes.trip.data.datasource.ItineraryLocalDataSourceImpl
import com.luisfagundes.trip.data.mapper.AirportMapper
import com.luisfagundes.trip.data.mapper.ItineraryItemMapper
import com.luisfagundes.trip.data.repository.ItineraryRepositoryImpl
import com.luisfagundes.trip.domain.repository.ItineraryRepository
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
            localDataSource = localDataSource,
            mapper = mapper
        )
    }
}