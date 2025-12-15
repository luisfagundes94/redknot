package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.model.withCalculatedStatus
import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class GetTripListUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(): Result<Map<TripStatus, List<Trip>>> {
        return repository.getTripList().map { trips ->
            trips.map { it.withCalculatedStatus() }.groupBy { it.status }
        }
    }
}