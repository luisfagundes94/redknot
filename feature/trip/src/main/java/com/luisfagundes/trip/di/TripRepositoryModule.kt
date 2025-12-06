package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.datasource.TripDataSource
import com.luisfagundes.trip.data.mapper.TripMapper
import com.luisfagundes.trip.data.repository.TripRepositoryImpl
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
        dataSource: TripDataSource,
        mapper: TripMapper
    ): TripRepository {
        return TripRepositoryImpl(
            dataSource = dataSource,
            mapper = mapper
        )
    }
}