package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

internal class ValidateDurationErrorUseCase @Inject constructor() {
    operator fun invoke(durationHours: Int, durationMinutes: Int): ItineraryValidationError? {
        val total = durationHours.hours + durationMinutes.minutes
        return if (total <= Duration.ZERO) ItineraryValidationError.INVALID_DURATION else null
    }
}
