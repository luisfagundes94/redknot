package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.datasource.ItineraryLocalDataSource
import com.luisfagundes.trip.data.datasource.TripLocalDataSource
import com.luisfagundes.trip.data.datasource.TripRemoteDataSource
import com.luisfagundes.trip.data.mapper.ItineraryItemMapper
import com.luisfagundes.trip.data.mapper.TripMapper
import com.luisfagundes.trip.data.repository.ItineraryRepositoryImpl
import com.luisfagundes.trip.data.repository.TripRepositoryImpl
import com.luisfagundes.trip.domain.repository.ItineraryRepository
import com.luisfagundes.trip.domain.repository.TripRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TripRepositoryModule {
    @Provides
    @Singleton
    fun provideTripRepository(
        localDataSource: TripLocalDataSource,
        remoteDataSource: TripRemoteDataSource,
        mapper: TripMapper
    ): TripRepository {
        return TripRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            mapper = mapper
        )
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