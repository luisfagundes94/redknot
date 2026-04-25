package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import javax.inject.Inject

internal class ValidateAirportCodeUseCase @Inject constructor() {
    private val iataPattern = Regex("^[A-Z]{3}$")

    operator fun invoke(code: String): ItineraryValidationError? {
        return when {
            code.isBlank() -> ItineraryValidationError.EMPTY_AIRPORT_CODE
            !iataPattern.matches(code) -> ItineraryValidationError.INVALID_AIRPORT_CODE
            else -> null
        }
    }
}
