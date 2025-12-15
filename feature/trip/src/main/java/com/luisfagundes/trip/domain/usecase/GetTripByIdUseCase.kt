package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.withCalculatedStatus
import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class GetTripByIdUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(id: Int): Result<Trip> {
        return repository.getTripById(id).map { trip ->
            trip.withCalculatedStatus()
        }
    }
}