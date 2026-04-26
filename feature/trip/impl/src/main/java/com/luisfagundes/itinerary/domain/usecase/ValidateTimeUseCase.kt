package com.luisfagundes.itinerary.domain.usecase

import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import java.time.LocalTime
import javax.inject.Inject

internal class ValidateTimeUseCase @Inject constructor() {
    operator fun invoke(time: LocalTime?): ItineraryValidationError? {
        return if (time == null) ItineraryValidationError.MISSING_TIME else null
    }
}
