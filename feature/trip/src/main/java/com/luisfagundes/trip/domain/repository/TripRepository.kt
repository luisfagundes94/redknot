package com.luisfagundes.trip.domain.repository

import com.luisfagundes.trip.domain.model.Trip

internal interface TripRepository {
    suspend fun getTripList(): Result<List<Trip>>
}