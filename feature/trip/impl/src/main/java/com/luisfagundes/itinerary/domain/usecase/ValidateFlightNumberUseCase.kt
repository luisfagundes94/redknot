package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import javax.inject.Inject

internal class ValidateFlightNumberUseCase @Inject constructor() {
    operator fun invoke(flightNumber: String): ItineraryValidationError? {
        return if (flightNumber.isBlank()) ItineraryValidationError.EMPTY_FLIGHT_NUMBER else null
    }
}
