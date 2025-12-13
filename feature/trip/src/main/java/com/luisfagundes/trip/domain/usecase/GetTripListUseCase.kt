package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.repository.TripRepository
import java.time.LocalDate
import javax.inject.Inject

internal class GetTripListUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(): Result<Map<TripStatus, List<Trip>>> {
        val today = LocalDate.now()

        return repository.getTripList().map { trips ->
            trips.map { trip ->
                trip.copy(
                    status = when {
                        trip.endDate.isBefore(today) -> TripStatus.PAST
                        trip.startDate.isAfter(today) -> TripStatus.UPCOMING
                        else -> TripStatus.ONGOING
                    }
                )
            }.groupBy { it.status }
        }
    }
}