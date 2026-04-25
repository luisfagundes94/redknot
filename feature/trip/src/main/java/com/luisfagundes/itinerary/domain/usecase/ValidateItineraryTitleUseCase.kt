package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import javax.inject.Inject

internal class ValidateItineraryTitleUseCase @Inject constructor() {
    operator fun invoke(title: String): ItineraryValidationError? {
        return if (title.isBlank()) ItineraryValidationError.EMPTY_TITLE else null
    }
}
