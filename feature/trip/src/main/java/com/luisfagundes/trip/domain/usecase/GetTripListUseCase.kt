package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.TripSection
import com.luisfagundes.trip.domain.model.TripSectionType
import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class GetTripListUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(): Result<List<TripSection>> {
        return repository.getTripList().map { trips ->
            val (upcomingTrips, pastTrips) = trips.partition { !it.done }

            buildList {
                if (upcomingTrips.isNotEmpty()) {
                    add(TripSection(TripSectionType.UPCOMING, upcomingTrips))
                }
                if (pastTrips.isNotEmpty()) {
                    add(TripSection(TripSectionType.PAST, pastTrips))
                }
            }
        }
    }
}