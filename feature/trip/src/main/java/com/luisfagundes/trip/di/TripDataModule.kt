package com.luisfagundes.trip.di

import com.luisfagundes.trip.data.dao.TripDao
import com.luisfagundes.common.data.database.TripDatabase
import com.luisfagundes.common.data.datasource.UnsplashRemoteDataSource
import com.luisfagundes.trip.data.datasource.TripLocalDataSource
import com.luisfagundes.trip.data.datasource.TripLocalDataSourceImpl
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
internal object TripDataModule {
    @Provides
    @Singleton
    fun provideTripDao(database: TripDatabase): TripDao {
        return database.tripDao()
    }

    @Provides
    @Singleton
    fun provideTripLocalDataSource(
        tripDao: TripDao
    ): TripLocalDataSource {
        return TripLocalDataSourceImpl(tripDao)
    }

    @Provides
    @Singleton
    fun provideTripMapper(): TripMapper {
        return TripMapper()
    }

    @Provides
    @Singleton
    fun provideTripRepository(
        tripDataSource: TripLocalDataSource,
        unsplashDataSource: UnsplashRemoteDataSource,
        mapper: TripMapper
    ): TripRepository {
        return TripRepositoryImpl(
            tripDataSource = tripDataSource,
            unsplashDataSource = unsplashDataSource,
            mapper = mapper
        )
    }
}