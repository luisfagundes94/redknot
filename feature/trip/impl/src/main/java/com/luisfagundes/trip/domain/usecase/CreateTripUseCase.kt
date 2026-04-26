package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class CreateTripUseCase @Inject constructor(
    private val repository: TripRepository,
) {
    suspend operator fun invoke(trip: Trip) = repository.createTrip(trip)
}