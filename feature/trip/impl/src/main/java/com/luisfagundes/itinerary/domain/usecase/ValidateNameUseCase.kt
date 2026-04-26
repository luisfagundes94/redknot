package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import javax.inject.Inject

internal class ValidateNameUseCase @Inject constructor() {
    operator fun invoke(name: String): ItineraryValidationError? {
        return if (name.isBlank()) ItineraryValidationError.EMPTY_NAME else null
    }
}
