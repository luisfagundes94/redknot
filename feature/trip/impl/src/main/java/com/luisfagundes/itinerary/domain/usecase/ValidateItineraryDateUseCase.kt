package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import java.time.LocalDate
import javax.inject.Inject

internal class ValidateItineraryDateUseCase @Inject constructor() {
    operator fun invoke(date: LocalDate?): ItineraryValidationError? {
        return if (date == null) ItineraryValidationError.MISSING_DATE else null
    }
}
