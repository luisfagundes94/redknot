package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripState
import com.luisfagundes.trip.domain.repository.TripRepository
import java.time.LocalDate
import javax.inject.Inject

internal class GetTripListUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(): Result<List<Trip>> {
        val today = LocalDate.now()

        return repository.getTripList().map { trips ->
            trips.map { trip ->
                trip.copy(
                    state = when {
                        trip.endDate.isBefore(today) -> TripState.PAST
                        trip.startDate.isAfter(today) -> TripState.UPCOMING
                        else -> TripState.ONGOING
                    }
                )
            }
        }
    }
}