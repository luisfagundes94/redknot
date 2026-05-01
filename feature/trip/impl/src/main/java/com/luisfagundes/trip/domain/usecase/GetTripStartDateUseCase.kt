package com.luisfagundes.trip.domain.usecase

import java.time.LocalDate
import javax.inject.Inject

internal class GetTripStartDateUseCase @Inject constructor(
    private val getTripByIdUseCase: GetTripByIdUseCase
) {
    suspend operator fun invoke(tripId: Int): LocalDate? {
        val tripStartDate = getTripByIdUseCase(tripId).getOrNull()?.startDate
        return if (LocalDate.now() > tripStartDate) LocalDate.now() else tripStartDate
    }
}