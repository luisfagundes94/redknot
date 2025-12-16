package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.database.TripDatabase
import com.luisfagundes.trip.data.datasource.TripLocalDataSource
import com.luisfagundes.trip.data.datasource.TripLocalDataSourceImpl
import com.luisfagundes.trip.data.datasource.TripRemoteDataSource
import com.luisfagundes.trip.data.datasource.TripRemoteDataSourceImpl
import com.luisfagundes.trip.data.mapper.TripMapper
import com.luisfagundes.trip.data.repository.TripRepositoryImpl
import com.luisfagundes.trip.data.service.UnsplashApiService
import com.luisfagundes.trip.domain.repository.TripRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TripDataModule {
    @Provides
    @Singleton
    fun provideTripLocalDataSource(
        database: TripDatabase
    ): TripLocalDataSource = TripLocalDataSourceImpl(database)

    @Provides
    @Singleton
    fun provideTripRemoteDataSource(
        apiService: UnsplashApiService
    ): TripRemoteDataSource = TripRemoteDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideTripMapper(): TripMapper {
        return TripMapper()
    }

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
}